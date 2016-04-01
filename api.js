var express = require('express'); //We need this for routing and http request/response purposes
var fs = require('fs');
https = require('https');
var app = express(); //Making an express instance
var bodyParser = require('body-parser'); //Need this to parse the body of an http request
var port = process.env.PORT || 8081; //Setting the port to 8081 for the server to run on

app.use(bodyParser.urlencoded({ extended: true})); //?
app.use(bodyParser.json()); //Need to this to be able to parse http requests for JSON

//Initializing routers
var messaging = require('./routes/messaging'); //Making a route for the messaging portion of the application
var user = require('./routes/user'); //Making a route for the user profile part of the application
var search = require('./routes/search'); //Making a route for the searching part of the application
var activiti = require('./routes/activiti'); //Making a route for the activiti search/creation part of the application
//This is used to create a secure http connection
https.createServer({
	key: fs.readFileSync('key.pem'),
	cert: fs.readFileSync('cert.pem')
}, app).listen(port);

app.use('/api/messaging', messaging); //Turning on the messaging route
app.use('/api/user', user); //Turning on the user route
app.use('/api/activiti', activiti); //Turning on the activiti route
app.use('/api/search', search); //Turning on the search route
// app.listen(port); //Telling the node server to listen on port 8081
console.log('Running on Port: ' + port); //DEBUG just seeing that it's actually running
