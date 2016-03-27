//Init
var express = require('express');
var router = express.Router();
var dbconnection = require('./node_modules/database/connectdb');

router.post('/', function(req,res){
	res.send('POST: Create a message');
	var query = "";
	dbconnection.sendQuery(query);

});

router.put('/', function(req,res){
	res.send('PUT: not currently implemented');
	var query = "";
	dbconnection.sendQuery(query);

});

router.delete('/', function(req,res) {
	res.send('DELETE: Delete Message ');
	var query = "";
	dbconnection.sendQuery(query);

});

router.get('/', function(req,res) {
	res.send('GET: Return MSG ');
	var query = "";
	dbconnection.sendQuery(query);

});


module.exports = router; 
