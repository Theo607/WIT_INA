#!/bin/bash

norris=$(curl -s https://api.chucknorris.io/jokes/random | jq -r '.value')
tmpfile=$(mktemp --suffix=.jpg)
curl -sL https://picsum.photos/70/70 --output "$tmpfile"
catimg -r 2 "$tmpfile" 2>/dev/null
echo "$norris"
rm "$tmpfile"

