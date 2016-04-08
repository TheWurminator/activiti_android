//Init
var express = require('express');
var passport = require('passport');
var router = express.Router();

//Redirect User to facebook
router.post('/', function(req,res){
	var s = passport.authenticate('facebook');
	res.send("Redirect to Facebook");
});

//Return from facebook after authentication
router.get('/', function(req,res) {
	res.send("Returned from Facebook");
});

// Exporting the functionality of the router to the calling module
module.exports = router; 
