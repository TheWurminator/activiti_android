this.pool = require('../node_modules/database/DBPool');

//Makes a new user based on FB graph response
exports.createUser = function(info,fbtoken,cb){
	var act_token = generateToken();
	var addQuery = "INSERT INTO users (uid, fb_token, activiti_token, first_name, bio, dob, gender, last_name) VALUES (\'" + info.id +"\', \'" + fbtoken +"\', \'" + act_token + "\', \'" + info.first_name + "\', \'efijeifjiejfijfeije\', \'" +  info.birthday + "\', \'" + info.gender + "\', \'" + info.last_name + "\');";
	this.pool.sendQuery(addQuery, function(response,err){
		if(err){
			console.log(err);
			if(cb !== null){
				cb(null);
			}
		}
		else{
			console.log("User successfully created");
			if(cb !== null){
				cb(response);
			}
		}
	});
};

//Generates a unique token
function generateToken(){
	var randtoken = require('rand-token');
	return randtoken.generate(255);
}

//Updates existing token
exports.updateFacebookToken = function(uid, fbtoken){
	var query = "update users set fb_token = \'" + fbtoken + "\' where users.uid = \'" + uid + "\'";
	this.pool.sendQuery(query, function(response){
	});
};

//Deletes a user
exports.deleteUser = function(uid,cb){
	var query = "delete from users where users.uid = \'" + uid + "\'";
	this.pool.sendQuery(query, function(response){
		console.log(response);
		cb(response);
	}); 
};

//Checks to see if user exists based on UID
exports.uidExists = function(uid, cb){
	var query = "select * from users where uid = \'" + uid + "\'";
	this.pool.sendQuery(query, function(response){
		//User Does not exist
		if(response.length < 1 || response === null || response == ""){
			cb(false);
		}
		//User does exist
		else{
			cb(true);
		}
	});
};

exports.getIDProfile = function(uid, cb){
	var query = "select * from users where uid = \'" + uid + "\'";
	this.pool.sendQuery(query, function(response){
		cb(response);
	})
}

//Gets profile information, sends it up through callback
exports.getProfile = function(token, cb){
	var query = "select * from users where activiti_token = \'" + token + "\'";
	this.pool.sendQuery(query, function(response){
		cb(response);
	});
};

//Updates user's information in the database
exports.updateProfile = function(userToken, info, cb){
	var dob = info[0]["dob"];
	var first_name = info[0]["first_name"];
	var last_name = info[0]["last_name"];
	var bio = info[0]["bio"];
	var gender = info[0]["gender"];
	var query2 = "update users set first_name = \'" + first_name + "\', last_name = \'" + last_name + "\', bio = \'" + bio + "\', gender = \'" + gender + "\' where users.activiti_token = \'" + userToken + "\'";
	this.pool.sendQuery(query, function(response){
		if(response == null){
			cb(null);
		}
		else{
			cb(response);
		}
	});
}


exports.tokenExists = function(token, cb){
	var query = "select * from users where activiti_token = \'" + token + "\'";
	this.pool.sendQuery(query, function(response){
		cb(true);
	});
}
