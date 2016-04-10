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
			userQueries.deleteUser(req.get('token'), function(response){

			});
		}
	});
});

//Fetches user profile information
router.get('/', function(req,res) {
	userQueries.getProfile(req.get('token'), function(profile) {
		res.send(profile);
	});
});

//This is used to expose the routers to the api.js (main module)
module.exports = router;
