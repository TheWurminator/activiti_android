//Import Modules
var express = require('express'); 
var router = express.Router();
var pool = require('../../node_modules/database/DBPool');
var tokenCheckReference = new require("../../node_modules/token-auth-check/tokenCheck"); 
var tokenChecker = new tokenCheckReference();
var usercommands = require('../../classes/usercommands');
//Update user profile information
router.put('/', function(req,res){
	tokenChecker.checkToken(req.body.userToken, function(response){
		if(response.contains("User")){
			res.send("User token is invalid");
		}
		else if(response.contains("Invalid")){
			res.send("Facebook token is invalid");
		}
		else{
			//Grab user information from db
			//Modify information
			//Put information back into db
		}
	});
});

//Deletes user from database
router.delete('/', function(req,res) {
	tokenChecker.checkToken(req.body.userToken, function(response){
		if(response.contains("User")){
			res.send("User token is invalid");
		}
		else if(response.contains("Invalid")){
			res.send("Facebook token is invalid");
		}
		else{
			//Find the user in the DB
			usercommands.deleteUser(req.body.userToken, function(response){
				console.log(response);
			});
			//Delete the user
		}
	});
});

//Fetches user profile information
router.get('/', function(req,res) {
	tokenChecker.checkToken(req.body.userToken, function(response){
		if(response.contains("User")){
			res.send("User token is invalid");
		}
		else if(response.contains("Invalid")){
			res.send("Facebook token is invalid");
		}
	});
});

//This is used to expose the routers to the api.js (main module)
module.exports = router;
