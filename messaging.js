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
	var query = "select * from employees";
	dbconnection.sendQuery(query, queryResponse, res);
	//Call function here to send response
	//Async
});

//Function to send the query
//THIS IS THE CALLBACK
function queryResponse(thing, res) {
	res.send(thing);
}


module.exports = router; 
