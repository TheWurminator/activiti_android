//Import External Module
var express = require('express');
var app = express();
var passport = require('passport');
var Strategy = require('passport-facebook').Strategy;
var router = express.Router();
var fbauth = require('../../fbauth.json');
var user_permissions = require('./fb_permissions/user_permissions.json');
var userQueries = require('../../queries/userQueries');
var graphcall = require('../../queries/fbQueries');

//Iniialize passport
router.use(passport.initialize()); 

//Called when user is logged in - redirected back from facebook
passport.use(new Strategy(fbauth, function(accessToken, refreshToken, profile, cb) {
    //Do the graph call
    console.log(profile.id);
    userQueries.uidExists(profile.id, function(response){
    	if(response == true){
    		//If a user already exists, update their token
    		userQueries.updateFacebookToken(profile.uid, accessToken);
    	}
    	else{
	 		//If a user doesn't exist, make a new one
    		graphcall.getUserInfo(accessToken, function(response){
    			userQueries.createUser(response, accessToken);
    		});
    	}
    });
    return cb(null, profile);
}));

passport.serializeUser(function(user, cb) {
  cb(null, user);
});

passport.deserializeUser(function(obj, cb) {
  cb(null, obj);
});

//Redirect User to facebook
router.get('/', passport.authenticate('facebook', user_permissions));

//Return from facebook after authentication
router.get('/return', passport.authenticate('facebook', user_permissions,
	function(req, res) {
		res.send('Facebook Redirected.');
	})
);

// Exporting the functionality of the router to the calling module
module.exports = router; 
