//Init
var express = require('express');
var app = express();
var passport = require('passport');
var Strategy = require('passport-facebook').Strategy;
var router = express.Router();
var facebookUser = require('../../classes/fbuser.js');
var fbauth = require('../../fbauth.json');
var user_permissions = require('./fb_permissions/user_permissions.json');

router.use(passport.initialize()); //Have to initialize passport before using it

//This is called when the user actually logs in
passport.use(new Strategy(fbauth, function(accessToken, refreshToken, profile, cb) {
    var user = new facebookUser(accessToken, profile.id); //Makes a new user
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
