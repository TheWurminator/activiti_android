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
var bodyParser = require('body-parser');
var jsonParser = bodyParser.json();
var token = null;
//Iniialize passport
router.use(passport.initialize()); 
app.use(jsonParser);

//Called when user is logged in - redirected back from facebook
passport.use(new Strategy(fbauth, function(accessToken, refreshToken, profile, cb) {
    //Do the graph call
    console.log("profile is : " + profile);
    userQueries.uidExists(profile.id, function(response){
    	if(response === true){
    		//If a user already exists, update their token
    		console.log("User Exists");
    	}
    	else{
	 	//If a user doesn't exist, make a new one
          console.log("User doesn't exist");
    		graphcall.getUserInfo(accessToken, function(fbres){
                console.log(fbres);
    			userQueries.fbCreateUser(token, fbres, accessToken, function(res){
                    if(res === null){
                        console.log("User not created successfully");
                    }
                });
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
router.get('/', generateToken, passport.authenticate('facebook', user_permissions));

//Return from facebook after authentication
router.get('/return', passport.authenticate('facebook', user_permissions), function(req, res) {
    res.redirect('/api/login/facebook/finish');
});

router.get('/finish', function(req,res,next){
        //console.log(req);
        res.status(200).send(token);
})

//Build middleware

function generateToken(req,res,next){
    var randtoken = require('rand-token');
    token = randtoken.generate(255);
    next();
}
// Exporting the functionality of the router to the calling module
module.exports = router;