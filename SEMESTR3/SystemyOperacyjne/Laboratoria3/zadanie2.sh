#!/bin/bash
# ============================
# SYSTEM MONITOR (stable)
# ============================

tput civis
trap 'tput cnorm; clear; exit' INT TERM

declare -A RX_PREV TX_PREV
declare -a RX_HIST TX_HIST
MAX_HISTORY=30

# ----------------------------
# Formatowanie bajtów
format_bytes() {
    local bytes=$1
    if (( bytes > 1048576 )); then
        printf "%.2f MB/s" "$(bc -l <<< "$bytes/1048576")"
    elif (( bytes > 1024 )); then
        printf "%.2f KB/s" "$(bc -l <<< "$bytes/1024")"
    else
        printf "%d B/s" "$bytes"
    fi
}

# ----------------------------
# Prosty wykres ASCII
draw_graph() {
    local -n hist=$1
    (( ${#hist[@]} == 0 )) && { echo "(brak danych)"; return; }
    local max=0 val scaled
    for val in "${hist[@]}"; do
        (( val > max )) && max=$val
    done
    (( max == 0 )) && { echo "(brak aktywności)"; return; }
    for val in "${hist[@]}"; do
        scaled=$((val * 10 / max))
        (( scaled < 1 )) && scaled=1
        printf "%s" "$(printf '█%.0s' $(seq 1 $scaled))"
        printf " "
    done
    echo
}

# ----------------------------
# CPU usage + frequency
get_cpu_usage() {
    mapfile -t LINES < <(grep '^cpu[0-9]' /proc/stat)
    for line in "${LINES[@]}"; do
        read -r cpu user nice system idle iowait irq softirq steal _ <<< "$line"
        total=$((user + nice + system + idle + iowait + irq + softirq + steal))
        prev_total_var="PREV_TOTAL_${cpu}"
        prev_idle_var="PREV_IDLE_${cpu}"
        prev_total=${!prev_total_var:-0}
        prev_idle=${!prev_idle_var:-0}
        diff_total=$((total - prev_total))
        diff_idle=$((idle - prev_idle))
        (( diff_total == 0 )) && diff_total=1
        usage=$((100 * (diff_total - diff_idle) / diff_total))
        eval "$prev_total_var=$total"
        eval "$prev_idle_var=$idle"

        freq_path="/sys/devices/system/cpu/${cpu}/cpufreq/scaling_cur_freq"
        if [[ -r $freq_path ]]; then
            freq=$(awk '{printf "%.1f", $1/1000}' "$freq_path")
            printf "%-5s %3d%% @ %6.1f MHz\n" "$cpu" "$usage" "$freq"
        else
            printf "%-5s %3d%%\n" "$cpu" "$usage"
        fi
    done
}

# ----------------------------
# Network usage (rx/tx)
get_net_usage() {
    local iface rx tx rx_rate tx_rate
    local any_activity=false

    while read -r iface rx _ _ _ _ _ _ tx _; do
        iface=${iface%:}
        [[ $iface == lo ]] && continue

        if [[ -n ${RX_PREV[$iface]} ]]; then
            rx_rate=$((rx - RX_PREV[$iface]))
            tx_rate=$((tx - TX_PREV[$iface]))

            # jeśli jakakolwiek aktywność — pokaż
            if (( rx_rate > 0 || tx_rate > 0 )); then
                any_activity=true
                printf "%s: ↓ %s | ↑ %s\n" "$iface" "$(format_bytes "$rx_rate")" "$(format_bytes "$tx_rate")"
            fi

            RX_HIST+=($rx_rate)
            TX_HIST+=($tx_rate)
            (( ${#RX_HIST[@]} > MAX_HISTORY )) && RX_HIST=("${RX_HIST[@]:1}")
            (( ${#TX_HIST[@]} > MAX_HISTORY )) && TX_HIST=("${TX_HIST[@]:1}")
        fi
        RX_PREV[$iface]=$rx
        TX_PREV[$iface]=$tx
    done < <(grep ':' /proc/net/dev)

    if ! $any_activity; then
        echo "(brak ruchu sieciowego)"
    fi
}

# ----------------------------
# Uptime
get_uptime() {
    local up sec min hr day
    read -r up _ < /proc/uptime
    sec=${up%.*}
    day=$((sec/86400))
    hr=$(( (sec%86400)/3600 ))
    min=$(( (sec%3600)/60 ))
    sec=$((sec%60))
    printf "Uptime: %d days %02d:%02d:%02d\n" "$day" "$hr" "$min" "$sec"
}

# ----------------------------
# Load average
get_loadavg() {
    awk '{printf "Load avg: %.2f %.2f %.2f\n", $1,$2,$3}' /proc/loadavg
}

# ----------------------------
# Memory usage
get_meminfo() {
    local total free avail used
    read -r _ total _ < <(grep MemTotal /proc/meminfo)
    read -r _ avail _ < <(grep MemAvailable /proc/meminfo)
    used=$((total - avail))
    printf "Memory: %d / %d kB (%.1f%%)\n" "$used" "$total" "$(bc -l <<< "100*$used/$total")"
}

# ----------------------------
# Battery
get_battery() {
    if [[ -r /sys/class/power_supply/BAT0/uevent ]]; then
        awk -F= '/POWER_SUPPLY_CAPACITY=/{printf "Battery: %s%%\n",$2}' /sys/class/power_supply/BAT0/uevent
    fi
}

# ----------------------------
# Pętla główna
clear
while true; do
    tput cup 0 0
    echo "=== SYSTEM MONITOR ==="
    echo
    get_uptime
    get_loadavg
    get_meminfo
    echo
    get_battery
    echo
    echo "CPU usage:"
    get_cpu_usage
    echo
    echo "Network:"
    get_net_usage
    echo
    echo "↓ RX history:"
    draw_graph RX_HIST
    echo "↑ TX history:"
    draw_graph TX_HIST
    sleep 1
done

