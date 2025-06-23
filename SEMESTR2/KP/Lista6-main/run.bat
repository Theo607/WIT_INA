@echo off

set /p compile_choice="compile [y/n]: "
if /i "%compile_choice%"=="y" (
    echo Compiling...
    javac GRID\*.java GUI\*.java App\*.java -d Out
    if %errorlevel% neq 0 (
        echo Compilation failed.
        exit /b %errorlevel%
    )
) else (
    echo Skipping compilation.
)

set /p run_choice="run [y/n]: "
if /i "%run_choice%"=="y" (
    echo Running application...
    java -cp Out App/App
    if %errorlevel% neq 0 (
        echo Application failed to run.
        exit /b %errorlevel%
    )
) else (
    echo Skipping run.
)

echo done.
exit /b 