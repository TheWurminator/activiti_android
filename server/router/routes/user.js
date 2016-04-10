//Import Modules
var express = require('express'); 
var router = express.Router();
var userQueries = require('../../queries/userQueries');

//Fetches user profile information
router.get('/', function(req,res) {
	userQueries.getProfile(req.get('token'), function(response) {
		res.send(response);
	});
});

//Update user profile information
router.put('/', function(req, res) {
	userQueries.updateProfile(req.get('token'), req.body, function(response) {
		res.send(response);
	});
});

//Deletes user from database
router.delete('/', function(req,res) {
	userQueries.deleteUser(req.get('token'), function(response){
		res.send(response);
	});
});

//This is used to expose the routers to the api.js (main module)
module.exports = router;
