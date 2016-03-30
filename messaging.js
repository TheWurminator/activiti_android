//Init
var express = require('express');
var router = express.Router();
var DBConnection = require('./node_modules/database/DBConnection');

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
	//Needed to re-instantiate the con in order to reuse it
	//Because otherwise, the reference is a dead connection after it's ended once
	var con = new DBConnection();
	var query = "select * from users";	
	con.sendQuery(query, queryResponse, res);
});

//Function to send the query
//THIS IS THE CALLBACK
function queryResponse(thing, res) {
	res.send(thing);
}

module.exports = router; 
