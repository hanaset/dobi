#!/bin/bash
app_name="dobi-api"
app_file="dobi-api.jar"
staging_file="dobi-api-staging.jar"

./shutdown.sh

# jar delete
if [ -f "$app_file" ]
then
	echo "$app_file found."
	rm ${app_file}
	echo "$app_file delete completed."
else
	echo "$app_file not found."
fi

# staging.war => war
if [ -f "$staging_file" ]
then
	echo "$staging_file => $app_file"
	mv ${staging_file} ${app_file}
fi

./start.sh
