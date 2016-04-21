//Import Modules
var express = require('express'); 
var bodyParser = require('body-parser');
var app = express();
var jsonParser = bodyParser.json();
var router = express.Router();
var userQueries = require('../../queries/userQueries');
var tagQueries = require('../../queries/tagQueries');

//Fetches user profile information
router.get('/', jsonParser, function(req,res) {
	if(req.get('uid')){
		console.log("Token is here");
		userQueries.getIDProfile(req.get('uid'), function(response){
			if(response == null){
				res.status(400).send("user not found");
			}
			else{
				res.status(200).send(response);
			}
		});
	}
	else{ //Will just fetch the user profile
		console.log("Token is not here");
		userQueries.getUIDfromToken(req.get('token'), function(response){
			if(response == null){
				res.status(400).send("user not found");
			}
			else{
				userQueries.getIDProfile(response, function(response) {
				if(response == null){
					res.status(400).send("User not found");
				}
				else{
					res.status(200).send(response);
				}
				});	
			}
		});
	}
});

//This is a function that will return a user's tags
//Takes in a user token and returns a JSON file with the tags
router.get('/tags', jsonParser, function(req,res){
	userQueries.getUIDfromToken(req.get('token'), function(response){
		if(response == null){
			res.sendStatus(400);
		}
		else{
			userQueries.getTagsUser(response, function(res2) {
				if(res2 == null){
					res.status(400).send("No tags found for this user");
				}
				else{
					console.log(JSON.stringify(res2));
					res.status(200).send(res2);
				}
			});				
		}
	});
});

//DEBUG
//Used to create users in the database
router.post('/', jsonParser, function(req,res){
	for(var x = 0; x < req.body.tags.length; x++){
		console.log(req.body.tags[x]);
	}
	var randtoken = require('rand-token');
    token = randtoken.generate(255);
	userQueries.debugCreateUser(token, req.body, "test_facebooktoken", function(response){
		if(response == null){
			res.status(400).send("User was not successfully created");
		}
		else{
			res.status(200).send("User was successfully created");
		}
	});	
});

//DEBUG
//Used to set more tags for a user
//Takes in a JSON containing a tags array
router.post('/tags', jsonParser, function(req,res){
	userQueries.getUIDfromToken(req.get('token'), function(response){
		if(response == null){
			res.status(400).send("User doesn't exist");
		}
		else{
			userQueries.setTags(response, req.body.tags, function(res2){
				if(res2 == null){
					res.status(400).send("Tags could not be created");
				}
				else{
					res.status(200).send("Tags successfully added to user");
				}
			});
		}
	});
});

//Update user profile information
//Takes in a JSON containing profile information
router.put('/', jsonParser, function(req, res) {
	userQueries.updateProfile(req.get('token'), req.body, function(response) {
		if(response === null){
			res.status(400).send("User profile not updated");
		}
		else{
			res.status(200).send(response);
		}
	});
});

//Deletes user from database
//Takes in token from header
//Deletes the user associated with that token
router.delete('/', jsonParser, function(req,res) {
	userQueries.deleteUser(req.get('token'), function(response){
		if(response === null || response.affectedRows == 0){
			res.status(400).send("Unable to delete user, user not found");
		}
		else{
			res.status(200).send("User deleted");
		}
	});
});

//This is used to expose the routers to the api.js (main module)
module.exports = router;
