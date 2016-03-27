//Init
var express = require('express');
var router = express.Router();
var dbconnection = require('./node_modules/database/connectdb');

router.post('/', function(req,res){
	res.send('Not Implemented');
	var query = "";
	dbconnection.sendQuery(query);
	dbconnection.endConnection();

});

router.put('/', function(req,res){
	res.send('Not Implemented');
	var query = "";
	dbconnection.sendQuery(query);
	dbconnection.endConnection();

});

router.delete('/', function(req,res) {
	res.send('Not Implemented');
	var query = "";
	dbconnection.sendQuery(query);
	dbconnection.endConnection();

});

router.get('/', function(req,res) {
	res.send('Send search filter');
	var query = "";
	dbconnection.sendQuery(query);
	dbconnection.endConnection();

});

module.exports = router; 

