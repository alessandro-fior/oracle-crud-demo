@echo off
setlocal

cd /d "%~dp0"

echo ============================================================
echo 01 - AVVIO APPLICAZIONE SPRING BOOT
echo ============================================================
echo Directory progetto: %CD%
echo Repository Maven: target\m2-repository
echo.
echo Avvio: mvn spring-boot:run
echo L'applicazione resta in esecuzione in questa finestra.
echo Per fermarla, premere CTRL+C.
echo ============================================================
echo.

mvn "-Dmaven.repo.local=target/m2-repository" spring-boot:run
set "RESULT=%ERRORLEVEL%"

echo.
echo ============================================================
echo Avvio terminato con codice: %RESULT%
echo ============================================================
exit /b %RESULT%
