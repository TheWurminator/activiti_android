//Init
var express = require('express'); //Needed for the web element of this
var router = express.Router(); //Need routers to direct API calls
var DBConnection = require('../../node_modules/database/DBConnection'); //Importing the custom module
var con = new DBConnection(); //Instantiating the DBConnection module for use in this module

//This is the function that creates a new user
//This will need to use facebook authentication
//TODO: Use passport-facebook and understand how this works 04/05 Got it
router.post('/', function(req,res){
	//Currently this is being used for testing purposes, these requests will be replaced soon
	console.log("Going to make a user now");
	var firstPart = "insert into users (id, first_name, last_name, age, location)\n";
	var secondPart = " values (NULL, 'charles', 'barkley', '55', 'tt');";
	
	con.sendQuery((firstPart+secondPart), queryResponse, res); //Sends a query to the database. (queryString, queryResponse)
	//con.endConnection();
});

//This should update a user's profile information
//The format for this will be in JSON
router.put('/', function(req,res){
	res.send('PUT: Update user info');
	var query = "";
});

//This is a function that will delete a user from the database
router.delete('/', function(req,res) {
	res.send('DELETE: Delete User details');
	var query = "";
});

//This is a function to simply fetch user profile information
router.get('/', function(req,res) {
	res.send('GET: Return user details');
	var query = "";
});

//This is a callback function that will return database information to the 
//user in the form of an http response
function queryResponse(thing, res) {
	res.send(thing); //Sends the passed in response back to the server
}

//This is used to expose the routers to the api.js (main module)
module.exports = router;

