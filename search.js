//Init
var express = require('express');
var router = express.Router();
var dbconnection = require('./node_modules/database/connectdb');

router.post('/', function(req,res){
	res.send('Not Implemented');
	var query = "";
	dbconnection.sendQuery(query);

});

router.put('/', function(req,res){
	res.send('Not Implemented');
	var query = "";
	dbconnection.sendQuery(query);

});

router.delete('/', function(req,res) {
	res.send('Not Implemented');
	var query = "";
	dbconnection.sendQuery(query);

});

router.get('/', function(req,res) {
	res.send('Send search filter');
	var query = "";
	dbconnection.sendQuery(query);

});

module.exports = router; 

