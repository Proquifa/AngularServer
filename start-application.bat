@echo off
echo ===================================================
echo Iniciando la aplicacion AngularServer
echo ===================================================
echo.
echo Compilando el proyecto...
call mvn clean install -DskipTests
echo.
echo Iniciando la aplicacion...
call mvn spring-boot:run
echo.
echo Si la aplicacion no inicia correctamente, revisa el README.md para mas informacion.
pause