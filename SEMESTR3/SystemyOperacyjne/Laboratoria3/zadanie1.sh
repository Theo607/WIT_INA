#!/bin/bash

flag=true
get_highest_pids() {
    local max_lines=${1:-20}
    local pids=()

    # Pobieramy wszystkie PID-y z /proc
    for pid_path in /proc/[0-9]*; do
        pid=${pid_path##*/}
        [[ -r /proc/$pid/stat ]] || continue
        pids+=("$pid")
    done

    # Sortujemy malejąco i zwracamy pierwsze max_lines PID-ów
    printf "%s\n" "${pids[@]}" | sort -nr | head -n "$max_lines"
}


print_pid_info() {
    local pid=$1
    local _pid comm state ppid pgid sid tty_nr rss

    [[ -r /proc/$pid/stat ]] || return

    read -r _pid comm state ppid pgid sid tty_nr _ < /proc/$pid/stat
    comm=${comm#(}
    comm=${comm%)}

    rss=0
    if [[ -r /proc/$pid/status ]]; then
        while IFS=':' read -r key val; do
            if [[ $key == "VmRSS" ]]; then
                rss=${val//[[:space:]]/}
                break
            fi
        done < /proc/$pid/status
    fi

    printf "%-6s %-6s %-20s %-4s %-8s %-10s %-6s %-6s\n" \
        "$ppid" "$pid" "$comm" "$state" "$tty_nr" "$rss" "$pgid" "$sid"
}

draw_pid() {
    local count=0
    for pid_path in $(get_highest_pids 20); do
        pid=${pid_path##*/}
        print_pid_info "$pid"
        ((count++))
        [[ $count -ge 20 ]] && break
    done
}


draw() {
    local buffer=""
    buffer+=" === MINIHTOP ===\n"
    buffer+="PPID   PID    COMM                 STATE TTY      RSS(kB)    PGID   SID\n"
    buffer+="-------------------------------------------------------------------------------\n"
    buffer+="$(draw_pid)\n"
    buffer+="-------------------------------------------------------------------------------\n"
    buffer+="$(date)\n"
    buffer+="[q] quit | [k] kill | [s] stop | [c] cont | [r] renice\n"
    printf "%b" "$buffer"
}

get_key() {
    local key
    read -n1 -t 0.3 key
    case "$key" in
        q) flag=false ;;
        k) tput cnorm; do_kill; tput civis ;;
        s) tput cnorm; do_stop; tput civis ;;
        c) tput cnorm; do_cont; tput civis ;;
        r) tput cnorm; do_renice; tput civis ;;
    esac
}


do_kill() {
    local pid
    tput cnorm
    printf "PID to kill: "
    read pid
    [[ -z "$pid" ]] && return
    if kill -TERM "$pid" 2>/dev/null; then
        echo "Killed PID $pid"
    else
        echo "Failed to kill PID $pid"
    fi
    sleep 1
    tput civis
}


do_stop() {
    local pid
    read -p "PID to stop: " pid
    [[ -z "$pid" ]] && return
    kill -STOP "$pid" 2>/dev/null || echo "Failed to stop PID $pid"
}

do_stop() {
    local pid
    tput cnorm
    printf "PID to stop: "
    read pid
    [[ -z "$pid" ]] && return
    kill -STOP "$pid" 2>/dev/null || echo "Failed to stop PID $pid"
    sleep 1
    tput civis
}

do_cont() {
    local pid
    tput cnorm
    printf "PID to continue: "
    read pid
    [[ -z "$pid" ]] && return
    kill -CONT "$pid" 2>/dev/null || echo "Failed to continue PID $pid"
    sleep 1
    tput civis
}

do_renice() {
    local pid prio
    tput cnorm
    printf "PID to renice: "
    read pid
    [[ -z "$pid" ]] && return
    printf "New priority (-20..19): "
    read prio
    [[ -z "$prio" ]] && return
    renice "$prio" -p "$pid" 2>/dev/null || echo "Failed to renice PID $pid"
    sleep 1
    tput civis
}


main() {
    clear
    tput smcup
    tput civis
    trap 'tput cnorm; tput rmcup; clear; exit' INT TERM

    while $flag; do
        tput cup 0 0
	printf "\n"
        draw
        get_key
    done

    tput cnorm
    tput rmcup
    clear
}

main

