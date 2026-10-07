@echo off
setlocal

cd /d "%~dp0"

echo ============================================================
echo 02 - TEST JUNIT MANUALI, UNO ALLA VOLTA
echo ============================================================
echo Directory progetto: %CD%
echo Profilo Spring: test
echo Repository Maven: target\m2-repository
echo.
echo Inserire il nome della classe senza estensione .java.
echo Esempi: BulkInsertTest, CacheEvictionTest, ComplexQueryTest
echo Per terminare, premere INVIO senza inserire un nome.
echo ============================================================
echo.

:ask_test
set "TEST_CLASS="
set /p "TEST_CLASS=Classe di test da eseguire: "
if not defined TEST_CLASS goto finished

echo.
echo ------------------------------------------------------------
echo Test selezionato: %TEST_CLASS%
echo Comando: mvn -Dspring.profiles.active=test test -Dtest=%TEST_CLASS%
echo ------------------------------------------------------------
echo.

mvn "-Dmaven.repo.local=target/m2-repository" "-Dspring.profiles.active=test" test "-Dtest=%TEST_CLASS%"
set "RESULT=%ERRORLEVEL%"

echo.
echo ------------------------------------------------------------
echo Test %TEST_CLASS% terminato con codice: %RESULT%
echo ------------------------------------------------------------
echo.
goto ask_test

:finished
echo.
echo Esecuzione manuale terminata.
exit /b 0
