var express = require('express'); //We need this for routing and http request/response purposes
var fs = require('fs');
var https = require('https');
var app = express(); //Making an express instance
var bodyParser = require('body-parser'); //Need this to parse the body of an http request
var port = process.env.PORT || 8081; //Setting the port to 8081 for the server to run on
var router = express.Router();
var passport = require('passport');
var Strategy = require('passport-facebook').Strategy;
var fbauth = require('./fbauth.json');
var facebookUser = require('./fbuser.js')
app.use(bodyParser.urlencoded({ extended: true})); 
app.use(bodyParser.json()); //Need to this to be able to parse http requests for JSON

// Configure the Facebook strategy for use by Passport.
//
// OAuth 2.0-based strategies require a `verify` function which receives the
// credential (`accessToken`) for accessing the Facebook API on the user's
// behalf, along with the user's profile.  The function must invoke `cb`
// with a user object, which will be set at `req.user` in route handlers after
// authentication.
// This happens when the user is authenticated
passport.use(new Strategy(fbauth,
  function(accessToken, refreshToken, profile, cb) {
    // In this example, the user's Facebook profile is supplied as the user
    // record.  In a production-quality application, the Facebook profile should
    // be associated with a user record in the application's database, which
    // allows for account linking and authentication with other identity
    // providers.
    var user = new facebookUser(); //Makes a new user
    user.setFacebookToken(accessToken); //Sets the access token for the new user
    console.log(user.fb_token); //DEBUG: Seeing if the user worked
    //TODO: Store this user in a database 
    //Hint: Uses the DBConnection module
    //Also possibly involves callbacks
    console.log("\n");
    console.log("Profile information: ");
    console.log(profile); 
    return cb(null, profile);
  }));


// Configure Passport authenticated session persistence.
//
// In order to restore authentication state across HTTP requests, Passport needs
// to serialize users into and deserialize users out of the session.  In a
// production-quality application, this would typically be as simple as
// supplying the user ID when serializing, and querying the user record by ID
// from the database when deserializing.  However, due to the fact that this
// example does not have a database, the complete Twitter profile is serialized
// and deserialized.
passport.serializeUser(function(user, cb) {
  cb(null, user);
});

passport.deserializeUser(function(obj, cb) {
  cb(null, obj);
});

//FOR TESTING
// Configure view engine to render EJS templates.
app.set('views', __dirname + '/views');
app.set('view engine', 'ejs');

//ALSO FOR TESTING
// Use application-level middleware for common functionality, including
// logging, parsing, and session handling.
app.use(require('morgan')('combined'));
app.use(require('cookie-parser')());
app.use(require('body-parser').urlencoded({ extended: true }));
app.use(require('express-session')({ secret: 'keyboard cat', resave: true, saveUninitialized: true }));

// Initialize Passport and restore authentication state, if any, from the
// session.
app.use(passport.initialize());
app.use(passport.session());

//Initializing routers
var messaging = require('./routes/messaging'); //Making a route for the messaging portion of the application
var user = require('./routes/user'); //Making a route for the user profile part of the application
var search = require('./routes/search'); //Making a route for the searching part of the application
var activiti = require('./routes/activiti'); //Making a route for the activiti search/creation part of the application
// var graph = require('./routes/graph'); //Making a route for Facebook Graph API calls

//This is used to create a secure https connection
https.createServer({
	key: fs.readFileSync('key.pem'), //Private Key
	cert: fs.readFileSync('cert.pem') //Public certificate
}, app).listen(port);


//TODO: Integrate facebook authentication routes MORE CLEANLY!!!
app.use('/api/messaging', messaging); //Turning on the messaging route
app.use('/api/user', user); //Turning on the user route
app.use('/api/activiti', activiti); //Turning on the activiti route
app.use('/api/search', search); //Turning on the search route
// app.use('/api/graph', graph); //Turning on the graph route
// Define routes.
app.get('/',
  function(req, res) {
    res.render('home', { user: req.user });
  });

app.get('/login',
  function(req, res){
    res.render('login');
  });

app.get('/login/facebook',
  passport.authenticate('facebook', { authType: 'rerequest', scope: ['user_friends', 'user_birthday', 'email', 'user_hometown', 'user_about_me', 'user_likes' ] ,failureRedirect: '/login' }));

app.get('/login/facebook/return', 
  passport.authenticate('facebook', { authType: 'rerequest', scope: ['user_friends', 'user_birthday', 'email', 'user_hometown'] ,failureRedirect: '/login' }),
  function(req, res) {
    res.redirect('/');
  });

app.get('/profile',
  require('connect-ensure-login').ensureLoggedIn(),
  function(req, res){
    res.render('profile', { user: req.user });
  });

app.use('/*', function(req,res){
	res.sendStatus(404);
})

console.log('Running on Port: ' + port); //DEBUG just seeing that it's actually running
