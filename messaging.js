//Init
var express = require('express');
var router = express.Router();
var dbconnection = require('./node_modules/database/connectdb');

router.post('/', function(req,res){
	res.send('POST: Create a message');
	var query = "";
	dbconnection.sendQuery(query);
	dbconnection.endConnection();

});

router.put('/', function(req,res){
	res.send('PUT: not currently implemented');
	var query = "";
	dbconnection.sendQuery(query);
	dbconnection.endConnection();

});

router.delete('/', function(req,res) {
	res.send('DELETE: Delete Message ');
	var query = "";
	dbconnection.sendQuery(query);
	dbconnection.endConnection();

});

router.get('/', function(req,res) {
	res.send('GET: Return MSG ');
	var query = "";
	dbconnection.sendQuery(query);
	dbconnection.endConnection();

});


module.exports = router; 
