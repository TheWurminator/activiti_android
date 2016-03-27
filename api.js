var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var port = process.env.PORT || 8081;

app.use(bodyParser.urlencoded({ extended: true}));
app.use(bodyParser.json());

//Initializing routers
var messaging =require('./messaging');
var user = require('./user');
var search = require('./search');
var activiti = require('./activiti');

app.use('/api/messaging', messaging);
app.use('/api/user', user);
app.use('/api/activiti', activiti);
app.use('/api/search', search);
app.listen(port);
console.log('Running on Port: ' + port);
