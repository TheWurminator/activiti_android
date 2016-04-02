#!/bin/bash

#Adding watch parameters so it will watch more than what is in the root directory


echo "Starting server"
nodemon --watch node_modules/database --watch ./ api.js
