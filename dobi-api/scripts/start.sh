#!/bin/bash
app_name="dobi-api"
app_file="dobi-api.jar"
profile="production"

# application start.
echo "$app_name is start."
nohup java -Dserver.port=5000 -Dspring.profiles.active=${profile} -jar ${app_file} > /dev/null &
