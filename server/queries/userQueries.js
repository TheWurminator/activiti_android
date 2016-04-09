this.pool = require('../node_modules/database/DBPool');

//Makes a new user based on FB graph response
exports.createUser = function(info,fbtoken){
	var act_token = generateToken();
	var addQuery = "INSERT INTO users (uid, fbtoken, activititoken, first_name, bio, dob, phone_number, gender) VALUES (\'" + info.userID +"\', \'" + fbtoken +"\', \'" + act_token + "\', \'" + info.first_name + "\', \'efijeifjiejfijfeije\', \'" +  info.birthday + "\', \'3213102110\', \'" + info.gender + "');";
	this.pool.sendQuery(addQuery, function(response,err){
		if(err){
			console.log(err)
		}
		else{
			console.log("User successfully created");
		}
	});
}

//Generates a unique token
function generateToken(){
	var randtoken = require('rand-token');
	return randtoken.generate(255);
}

//Updates existing token
exports.updateFacebookToken = function(uid, fbtoken){
	var query = "update users set token = " + fbtoken + "where users.uid = " + uid;
	this.pool.sendQuery(query, function(response){
		console.log(response);
		console.log("Hopefully token succesfully changed");
	})
}

//Deletes a user
exports.deleteUser = function(uid,cb){
	var query = "delete from users where \'users\'.\'uid\' = " + uid;
	this.pool.sendQuery(query, function(response){
		cb(response);
	}); 
}

//Checks to see if user exists based on UID
exports.uidExists = function(uid, cb){
	var query = "select * from users where uid = \'" + uid + "\'";
	console.log("Query is : " + query);
	this.pool.sendQuery(query, function(response){
		if(response.length < 1){
			cb(false);
		}
		else{
			cb(true);
		}
	});
}

//Finds a user based on UID
exports.findUser = function(uid,cb){
	var query = "select * from users where uid = " + uid;
	this.pool.sendQuery(query, function(err, rows){
		if(response.length < 1){
			cb("Not found");
		}
		else{
			cb(response);
		}
	});
}