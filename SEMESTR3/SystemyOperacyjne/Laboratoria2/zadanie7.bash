#!/bin/bash

for item in *; do
    lowercase="$(echo "$item" | tr '[:upper:]' '[:lower:]')"

    if [[ "$item" != "$lowercase" ]]; then
        mv -i -- "$item" "$lowercase"
    fi
done
