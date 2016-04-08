var express = require('express'); //We need this for routing and http request/response purposes
var app = express(); //Making an express instance
var fs = require('fs');
var https = require('https');
var router = require('./router')(app);
var port = process.env.PORT || 8081; //Setting the port to 8081 for the server to run on

//This is used to create a secure https connection
https.createServer({
	key: fs.readFileSync('key.pem'),
	cert: fs.readFileSync('cert.pem')
}, app).listen(port);

//Debug Message
console.log('Server Running on Port: ' + port);
