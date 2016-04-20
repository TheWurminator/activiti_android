var pool = require('../node_modules/database/DBPool');
var tagQueries = require('./tagQueries');
var itself = require('./userQueries');
var x = 0;
//Creates a new user, ONLY USED FOR DEBUGGING PURPOSES
exports.debugCreateUser = function(usertoken, info, fbtoken, cb){
	var act_token = usertoken;
	var addQuery = "INSERT INTO users (uid, fb_token, activiti_token, first_name, bio, dob, gender, last_name) VALUES (\'" + info.uid +"\', \'" + fbtoken +"\', \'" + act_token + "\', \'" + info.first_name + "\', \'"+ info.bio + "\', \'" +  info.dob + "\', \'" + info.gender + "\', \'" + info.last_name + "\');";
	pool.sendQuery(addQuery, function(res1){
		if(res1 == null){ //Bad set
			console.log(null);
			cb(null);
		}
		else{ //Able to create user
			console.log("User successfully created");
			//Time to add the tags for the user
			itself.getUIDfromToken(usertoken, function(res2){
				if(res2 == null){ //If the uid doesn't exist
					cb(null) //SHOULD NEVER EVEN HAPPEN
				}
				else{ //If we get the uid, we need to set the tags
					console.log("uid should be : " + res2);
					itself.setTags(res2, info.tags, function(res3){
						if(res3 == null){
							cb(null);
						}
						else{
							cb(true);
						}
					});
				}
			});
		}
	});
};

//This is a function to specifically create a user from facebook, this is used in production
exports.fbCreateUser = function(userToken, fbResponse, activiti_token, cb){
	var addQuery = "insert into users (uid, fb_token, activiti_token, dob, first_name, bio, gender, last_name) values (\'" + fbResponse.id + "\', \'" + userToken + "\', \'" + activiti_token + "\', \'" + fbResponse.birthday + "\', \'" + fbResponse.first_name + "\', \'" + "fbBio" + "\', \'" + fbResponse.gender + "\', \'" + fbResponse.last_name + "\')";
	pool.sendQuery(addQuery, function(response){
		if(response == null){
			console.log("Facebook user not added");
			cb(null);
		}
		else{
			cb(true);
		}
	});
}


//Updates existing token
// exports.updateFacebookToken = function(uid, fbtoken){
// 	var query = "update users set fb_token = \'" + fbtoken + "\' where users.uid = \'" + uid + "\'";
// 	this.pool.sendQuery(query, function(response){
// 		if(res)
// 	});
// };

//This is a function that takes in a user token and returns that user's uid
exports.getUIDfromToken = function(usertoken, cb){
	var query = "select * from users where activiti_token = \'"+ usertoken +"\'";
	pool.sendQuery(query, function(response){
		if(response == null){
			console.log("There was an error in getUIDfromToken");
			cb(null);
		}
		else{
			cb(response[0]['uid']);
		}
	});
}

//Deletes a user
exports.deleteUser = function(token,cb){
	var query = "delete from users where users.activiti_token = \'" + token + "\'";
	pool.sendQuery(query, function(response){
		if(response == null){
			cb(null);
		}
		else{
			console.log(response);
			cb(response);
		}
	}); 
};

//Checks to see if user exists based on UID
exports.uidExists = function(uid, cb){
	var query = "select * from users where uid = \'" + uid + "\'";
	pool.sendQuery(query, function(response){
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


//This will return a user's profile information given a UID
exports.getIDProfile = function(uid, cb){
	var query = "select * from users where uid = \'" + uid + "\'";
	pool.sendQuery(query, function(response){
		if(response == null){
			cb(null);
		}
		else{
			//Removing the sensitive information from the response
			var alteredResponse = response;
			delete alteredResponse[0]['activiti_token'];
			delete alteredResponse[0]['fb_token'];
			cb(alteredResponse[0]);
		}
	});
}


//Updates user's information in the database
exports.updateProfile = function(userToken, info, cb){
	itself.getUIDfromToken(userToken, function(response){
		if(response == null){
			console.log("This is broken");
			cb(null);
		}
		else{
			for(x in info){
				if(x != "tags"){
					var query = "update users set " + x + " = \'" + info[x] + "\' where users.uid = \'" + response + "\'" ;
					console.log(query);
					pool.sendQuery(query, function(res2){
						if(response == null || response.affectedRows < 1){
							console.log("This query did not do what was intended");
						}
						else{
							console.log("Query is successful: ");
						}
					});
				}
				else{
					itself.setTags(response, x, function(res3){

					});
				}
			}
		cb(true);
		}});
}

//This is a function that checks if a token exists in the database
exports.tokenExists = function(token, cb){
	var query = "select * from users where activiti_token = \'" + token + "\'";
	pool.sendQuery(query, function(response){
		if(response == null){
			cb(null);
		}
		else{
			cb(true);
		}	
	});
}

//This is a function that will set the tags for the user
//Takes in a uid and json w/tags
exports.setTags = function(uid, tags, cb){
	for(x = 0; x < tags.length; x++){ //Need ot run through the available tags
		console.log("This is " + tags[x]);
		var save = tags[x].toLowerCase();
		tagQueries.createTag(save, function(response){
			if(response == null){
				console.log("There was an error creating the tag");
				cb(null);
			}
			else{
				//The tag was created, now we need to do something about it
				console.log("Inside of create tag save is : " + response);
				setTagUser(uid, response, function(res){
					if(res == null){
						console.log("Could not add the tag, for some reason");
					}
					else{

					}
				});
			}
		});
	}
	cb(true);
}

function setTagUser(uid, tid, cb){
	var query = "insert into user_tags (uid, tid) values (\'" + uid + "\', \'" + tid + "\')";
	console.log(query);
	pool.sendQuery(query, function(response){
		if(response == null){
			console.log("Tag could not be added");
			cb(null);
		}
		else{
			console.log("tag successfully added");
			cb(true);
		}
	});
}

exports.getTagsUser = function(uid, cb){
	var query = "select * from user_tags where uid = \'" + uid +"\'";
	pool.sendQuery(query, function(response){
		if(response == null){
			cb(null);
		}
		else{
			cb(response);
		}
	});
}
