#/bin/bash

echo "Enter activiti.servebeer.com ssh username: "
read NAME

scp "$NAME"@activiti.servebeer.com:/home/central/credentials.tar.gz ./
tar xvf credentials.tar.gz
mv connection.json $PWD/node_modules/database/
rm -f credentials.tar.gz

