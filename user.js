//Init
var express = require('express');
var router = express.Router();
var dbconnection = require('./node_modules/database/connectdb');

router.post('/', function(req,res){
	res.send('POST: Make new user');
	var query = "";
	dbconnection.sendQuery(query);
	dbconnection.endConnection();

});

router.put('/', function(req,res){
	res.send('PUT: Update user info');
	var query = "";
	dbconnection.sendQuery(query);
	dbconnection.endConnection();

});


router.delete('/', function(req,res) {
	res.send('DELETE: Delete User details');
	var query = "";
	dbconnection.sendQuery(query);
	dbconnection.endConnection();

});

router.get('/', function(req,res) {
	res.send('GET: Return user details');
	var query = "";
	dbconnection.sendQuery(query);
	dbconnection.endConnection();

});

module.exports = router; 

