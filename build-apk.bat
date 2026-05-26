@echo off
REM Reddit Clone - APK Build Script for Windows
REM This script builds Debug and Release APKs locally

echo =========================================
echo   Reddit Clone - APK Build Script
echo =========================================
echo.

REM Clean previous builds
echo Cleaning previous builds...
call gradlew.bat clean

REM Build Debug APK
echo.
echo Building Debug APK...
call gradlew.bat assembleDebug --stacktrace

if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo [SUCCESS] Debug APK built successfully!
    echo   Location: app\build\outputs\apk\debug\app-debug.apk
    dir app\build\outputs\apk\debug\app-debug.apk
) else (
    echo [ERROR] Debug APK build failed!
    exit /b 1
)

REM Build Release APK
echo.
echo Building Release APK...
call gradlew.bat assembleRelease --stacktrace

if exist "app\build\outputs\apk\release\app-release-unsigned.apk" (
    echo [SUCCESS] Release APK built successfully!
    echo   Location: app\build\outputs\apk\release\app-release-unsigned.apk
    dir app\build\outputs\apk\release\app-release-unsigned.apk
) else (
    echo [ERROR] Release APK build failed!
    exit /b 1
)

REM Summary
echo.
echo =========================================
echo Build Complete!
echo =========================================
echo.
echo APK Files:
echo   * Debug:   app\build\outputs\apk\debug\app-debug.apk
echo   * Release: app\build\outputs\apk\release\app-release-unsigned.apk
echo.
echo Install with ADB:
echo   adb install app\build\outputs\apk\debug\app-debug.apk
echo.
pause
