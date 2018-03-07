@echo off

set JAVA_HOME="%JAVA_HOME%"

echo JAVA_HOME=%JAVA_HOME%

cd /d %~dp0

echo WORKING_DIRECTORY=%~dp0

SET RUN_STRING="%JAVA_HOME%\bin\java" -Dfile.encoding=UTF-8 -Xms10m -Xmx768m -cp %~dp0\target\dictionary-server.jar; "ru.ezhov.dictionary.webserver.App"

echo run command:
echo %RUN_STRING%

call %RUN_STRING%

pause