//User class
//Pulls in user data from Facebook and makes a new user
//Or pulls in existing user data from database

function fbUser(fbtoken, uid) {
	this.graph = require('fbgraph');
	this.userID = uid;
	this.fb_token = fbtoken; //User's Facebook token
	this.first_name, this.last_name, this.birthday, this.age, this.act_token, this.gender = null;
	this.DBConnection = require('../node_modules/database/DBConnection'); //Importing the custom module
	this.con = new this.DBConnection(); //Instantiating the DBConnection module for use in this module
	this.queryDB(this.userID); //Looks in the database for the specified user
}

//The whole point of this function is to see whether a user is present already or not
//If they are not present, we will need to generate a new activiti token for them
//Also we will need to add their facebook token into the database
//If the user is here, we will just return their entry from the database, in JSON
fbUser.prototype.queryDB = function(uid){
	var searchQuery = "select * from users where uid = " + this.userID;
	console.log(searchQuery);
	var currentRef = this; //Need to keep a reference to this object to use in the DB query's callback function
	this.con.sendQuery(searchQuery, function(response, err){
		console.log(response.length);
		if(response.length < 1 ){
			console.log("User was not found, creating a new user");
			currentRef.setupNewUser();
		}
		else{
			console.log("The user was found, pulling fields into object from database");
			currentRef.setupExistingUser(response);
		}
	});
}

//NEED TO IMPLEMENT
//Parses credentials from db found in the response passed in
fbUser.prototype.setupExistingUser = function(response){
	console.log(response);
}

fbUser.prototype.setupNewUser = function(){
	this.act_token = this.generateToken();
	var currentRef = this;
	//FBtoken is already set up from the constructor
	var facebookParameters = "fields=first_name, last_name, birthday, gender&"
	this.graph
	  .setAccessToken(this.getFacebookToken())
	  .get("/me?" + facebookParameters, function(err, data) {
	      currentRef.first_name = data.first_name;
	      currentRef.last_name = data.last_name;
	      currentRef.birthday = data.birthday;
	      currentRef.gender = data.gender;
	      currentRef.createUser(); //Calling a function to create a new user
	  });
}

//Successfully adds a user to the database based on the information that they 
fbUser.prototype.createUser = function(){
	//Make a query to add a user to the database
	var addQuery = "INSERT INTO users (uid, fbtoken, activititoken, first_name, bio, dob, phone_number, gender) VALUES (\'" + this.userID +"\', \'" + this.fb_token +"\', \'" + this.act_token + "\', \'" + this.first_name + "\', \'efijeifjiejfijfeije\', \'" +  this.birthday + "\', \'98498585\', \'" + this.gender + "');";
	this.con.sendQuery(addQuery, function(response,err){
		if(err){
			console.log(err)
		}
		else{
			console.log("user successfully created");
		}
	});
}

//This generated a unique token for a user
fbUser.prototype.generateToken = function(){
	this.randtoken = require('rand-token');
	return this.randtoken.generate(255); //User's ActivitI token
}

fbUser.prototype.getFirstName = function(){
	return this.first_name;
}

fbUser.prototype.getLastName = function(){
	return this.last_name;
}

fbUser.prototype.getBirthday = function(){
	return this.birthday;
}

fbUser.prototype.getInformation = function(){
	return null;
}

fbUser.prototype.storeInDB = function(userInformation){
	//Puts the json file, parses it and puts it into the db
}

fbUser.prototype.getFacebookToken = function(){
	return this.fb_token;
}

module.exports = fbUser; //Exporting the instantiation of the class