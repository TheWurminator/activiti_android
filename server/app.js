//Instantiate Modules
var app = require('express')();
var fs = require('fs'); //Access ssl key and cert
var https = require('https'); //Enable ssl (secure connection)
var port = process.env.PORT || 8081; //Configure port for server to listen
var router = require('./router')(app); //Configure routes

// Create server using secure ssl connection on specified port
https.createServer({
	key: fs.readFileSync('key.pem'), //Private Key
	cert: fs.readFileSync('cert.pem') //Public certificate
}, app).listen(port);

// app.listen(port);
//Debug Message
console.log('Server Running on Port: ' + port);
