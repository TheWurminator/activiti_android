//Import Modules
var express = require('express'); 
var app = express();
var router = express.Router();
var userQueries = require('../../queries/userQueries');
var tagQueries = require('../../queries/tagQueries');
var bodyParser = require('body-parser');
app.use(bodyParser.json());

//Fetches user profile information
router.get('/', function(req,res) {
	userQueries.getProfile(req.get('token'), function(response) {
		if(response === null){
			res.status(400).send("User not found");
		}
		else{
			res.status(200).send(response);
		}
	});
});

//This is a function that will return a user's tags
//Takes in a user token and returns a JSON file with the tags
router.get('/tags', function(req,res){
	tagQueries.getTagsUser(req.get('token'), function(response) {
		if(response == null){
			res.status(400).send("No tags found for this user");
		}
		else{
			res.status(200).send(response);
		}
	})
})


router.post('/', function(req,res){
	var randtoken = require('rand-token');
    token = randtoken.generate(255);
	userQueries.createUser(token, req.body, "test_facebooktoken", function(response){
		if(response === null){
			res.status(400).send("User was not successfully created");
		}
		else{
			res.status(200).send("User was successfully created");
		}
	});	
});

//Update user profile information
router.put('/', function(req, res) {
	userQueries.updateProfile(req.get('token'), req.body, function(response) {
		if(response === null){
			res.status(400).send("User profile not updated");
		}
		else{
			res.status(200).send(response);
		}
	});
});

//Deletes yourself from database
router.delete('/', function(req,res) {
	userQueries.deleteUser(req.get('token'), function(response){
		if(response === null || response.affectedRows === 0){
			res.status(400).send("Unable to delete user, user not found");
		}
		else{
			res.status(200).send("User deleted");
		}
	});
});

//This is used to expose the routers to the api.js (main module)
module.exports = router;
