@echo off
setlocal
cd /d "%~dp0"
set GRADLE_HOME=%LOCALAPPDATA%\MCroyaleMenu\gradle-8.14.3
if not exist "%GRADLE_HOME%\bin\gradle.bat" (
  echo Downloading Gradle 8.14.3. This is needed to compile the mod...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; $d=Join-Path $env:LOCALAPPDATA 'MCroyaleMenu'; New-Item -ItemType Directory -Force -Path $d ^| Out-Null; Invoke-WebRequest 'https://services.gradle.org/distributions/gradle-8.14.3-bin.zip' -OutFile (Join-Path $d 'gradle.zip'); Expand-Archive -Force (Join-Path $d 'gradle.zip') $d; Remove-Item (Join-Path $d 'gradle.zip') -Force"
  if errorlevel 1 (echo Gradle download failed. Check your internet connection. & pause & exit /b 1)
)
call "%GRADLE_HOME%\bin\gradle.bat" build
if errorlevel 1 (echo Build failed. Copy the error text and send it to ChatGPT. & pause & exit /b 1)
echo.
echo SUCCESS! Your mod is in build\libs\mcroyale-menu-1.0.0.jar
echo Copy that JAR into your Minecraft mods folder.
pause
