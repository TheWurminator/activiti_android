#!/bin/bash

#Checks to see if the SSL files are here
if [ ! -e "key.pem" ] || [ ! -e "cert.pem" ] || [ ! -e "node_modules/database/connection.json" ] || [ ! -e "fbauth.json" ];
then
		echo "Fetching credentials"
		bash scripts/getcred.sh
fi
#Starting the node server
echo "Starting server"
nodemon app.js
