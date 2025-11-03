#!/bin/bash

read -p "compile [y/n]: " compile_choice
if [[ "${compile_choice,,}" == "y" ]]; then
    echo "Compiling..."
    javac GRID/*.java GUI/*.java App/*.java -d Out
    if [[ $? -ne 0 ]]; then
        echo "Compilation failed."
        exit $?
    fi
else
    echo "Skipping compilation."
fi

read -p "run [y/n]: " run_choice
if [[ "${run_choice,,}" == "y" ]]; then
    echo "Running application..."
    java -cp Out App/App
    if [[ $? -ne 0 ]]; then
        echo "Application failed to run."
        exit $?
    fi
else
    echo "Skipping run."
fi

echo "done."
exit 0
