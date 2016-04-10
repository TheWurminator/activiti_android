//Import Modules
var express = require('express'); 
var router = express.Router();
var pool = require('../../node_modules/database/DBPool');
var userQueries = require('../../queries/userQueries');
var tokenChecker = require('../../node_modules/token-auth-check/tokenCheck');


//Deletes user from database
router.delete('/', function(req,res) {
	tokenChecker.checkToken(req.get('token'), function(response){
		if(response.contains("User")){
			res.send("User token is invalid");
		}
		else if(response.contains("Invalid")){
			res.send("Facebook token is invalid");
		}
		else{
			//Find the user in the DB
			userQueries.deleteUser(req.get('token'), function(response){
				//console.log(response);
			});
			//Delete the user
		}
	});
});

//Fetches user profile information
router.get('/', function(req,res) {
	tokenChecker.checkToken(req.get('token'), function(response){
		if(response === true){
			res.send("User token is valid");
		}
		else if(response === false){
			res.sendStatus(403);
		}
	});
});

//This is used to expose the routers to the api.js (main module)
module.exports = router;
