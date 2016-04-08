//Init
var express = require('express');
var app = express();
var bodyParser = require('body-parser'); //Need this to parse the body of an http request
var passport = require('passport');
var Strategy = require('passport-facebook').Strategy;
var router = express.Router();
var facebookUser = require('../../classes/fbuser.js');
var fbauth = require('../../fbauth.json');
app.use(require('cookie-parser')());
app.use(require('body-parser').urlencoded({ extended: true }));
app.use(bodyParser.json()); //Need to this to be able to parse http requests for JSON
app.use(require('express-session')({ secret: 'keyboard cat', resave: true, saveUninitialized: true }));
app.use(passport.session());

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
router.get('/', 
	 passport.authenticate('facebook', { authType: 'rerequest', scope: ['user_birthday', 'user_about_me', 'public_profile'] ,failureRedirect: '/login/facebook' }));

//Return from facebook after authentication
router.get('/return', passport.authenticate('facebook', { authType: 'rerequest', scope: ['user_birthday', 'user_about_me', 'public_profile'] ,failureRedirect: '/login' },
	  function(req, res) {
	    res.redirect('/');
	  })
);

// Exporting the functionality of the router to the calling module
module.exports = router; 
