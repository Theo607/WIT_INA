#!/bin/bash

LOGFILE="monitor.log"

# Kolory
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[0;33m'
NC='\033[0m'

inotifywait -m -r -e create -e modify -e delete --format "%w %e %f" . |
while read path event file; do
    # Pomijaj zdarzenia dotyczące monitor.log (nawet w podkatalogach)
    [[ "${path}${file}" == *"$LOGFILE" ]] && continue

    TIMESTAMP="$(date '+%Y-%m-%d %H:%M:%S')"
    LINE="[$TIMESTAMP] $event → ${path}${file}"

    # Zapis do logu (bez koloru)
    echo "$LINE" >> "$LOGFILE"

    # Wyświetlanie kolorowe
    case "$event" in
        CREATE*)
            echo -e "${GREEN}$LINE${NC}"
            ;;
        DELETE*)
            echo -e "${RED}$LINE${NC}"
            ;;
        MODIFY*)
            echo -e "${YELLOW}$LINE${NC}"
            ;;
        *)
            echo "$LINE"
            ;;
    esac
done

