//Init
var express = require('express');
var router = express.Router();
var DBConnection = require('./node_modules/database/DBConnection');
var con = new DBConnection();

router.post('/', function(req,res){
	res.send('POST: Create a message');
	var query = "";
});

router.put('/', function(req,res){
	res.send('PUT: not currently implemented');
	var query = "";
});

router.delete('/', function(req,res) {
	res.send('DELETE: Delete Message ');
	var query = "";
});

router.get('/', function(req,res) {
	var query = "select * from users";	
	con.sendQuery(query, queryResponse, res);
});

//Function to send the query
//THIS IS THE CALLBACK
function queryResponse(thing, res) {
	res.send(thing);
}

module.exports = router; 
