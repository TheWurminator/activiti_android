//Init
var express = require('express');
var router = express.Router();
var DBConnection = require('./node_modules/database/DBConnection');
<<<<<<< HEAD
=======
var con = new DBConnection();
>>>>>>> 75ae2ae47d6406c043e47340753fe3e6d73d2e16

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
<<<<<<< HEAD
	var con = new DBConnection();
=======
>>>>>>> 75ae2ae47d6406c043e47340753fe3e6d73d2e16
	var query = "select * from users";	
	con.sendQuery(query, queryResponse, res);
	con.endConnection();
});

//Function to send the query
//THIS IS THE CALLBACK
function queryResponse(thing, res) {
	res.send(thing);
}

module.exports = router; 
