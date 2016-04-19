var pool = require('../node_modules/database/DBPool');
var tagQueries = require('./tagQueries');
this.userQueries = require('./userQueries');
var itself = require('./activitiQueries');
//Makes a new user based on FB graph response
exports.createActiviti = function(info, userToken, cb){
	//This will catch an error, so the entire server doesn't crash when the JSON is wrong
	try {
		var addQuery = "insert into activitis (aid, name, description, cost, max_attendees, start_date, end_date, start_time, end_time, latitude, longitude, uid) values (\'null\', + \'" + info.name + "\', \'" + info.cost + "\', \'" + info.description + "\', \'" + info.max_attendees + "\', \'" + info.start_date + "\', \'" + info.end_date + "\', \'" + info.start_time + "\', \'" + info.end_time + "\', \'" + info.latitude + "\', \'" + info.longitude + "\', \'" + userToken + "\')"
		pool.sendQuery(addQuery, function(response){
			if(response == null){
				console.log("It broke");
				cb(null);
			}
			else{
				console.log(response);
				itself.setTags(response.insertId, info.tags, function(resp){
					if(resp == null){
						cb(null);
					}
					else{
						cb(true);
					}
				});
			}
		});
	}
	catch(e){
		console.log("Some fields were not found, incorrect JSON createActiviti");
		cb(null);
	}
};

//Deletes a activit
exports.deleteActiviti = function(aid, cb){
	var query = "delete from activitis where activities.aid = \'" + aid + "\'";
	pool.sendQuery(query, function(response){
		if(err){
			cb(null);
		}
		else{
			cb(response);
		}
	}); 
};

//This is a function that will set the tags for the user
//Takes in a uid and json w/tags
exports.setTags = function(aid, tags, cb){
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
				setTagActiviti(aid, response, function(res){
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

function setTagActiviti(aid, tid, cb){
	var query = "insert into activiti_tags (aid, tid) values (\'" + aid + "\', \'" + tid + "\')";
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

//Checks to see if Activiti exists
exports.activitiExists = function(uid, cb){
	var query = "";

	pool.sendQuery(query, function(response){
		//activiti Does not exist
		if(response.length < 1){
			cb(false);
		}
		//activiti does exist
		else{
			cb(true);
		}
	});
};

//Gets activiti information, sends it up through callback
exports.getActiviti = function(aid, cb){
	console.log("Fetching activiti");
	var query = "select * from activitis where aid = \'" + aid + "\'";
	pool.sendQuery(query, function(response, err){
		if(err){
			console.log(err);
		}
		if(response.length < 1){ //Response can be an empty set
			cb(null);
		}
		else{
			cb(response);
		}				
	});
};

//Update activiti information
exports.updateActiviti = function(userToken, info, cb){
	var query = "select * from activitis";
	pool.sendQuery(query, function(response,err){
		if(err){
			console.log(err);
		}
		else{
			console.log("Activiti successfully updated");
		}
	});
}

//This is a function that will fetch the tags for an activiti
//Takes in an activiti id, and returns a json with tags
exports.getTagsActiviti = function(activiti_id, cb){
	var query = "select * from activiti_tags where aid = \'" + activiti_id + "\'";
	pool.sendQuery(query, function(response){
		if(response == null){
			cb(null);
		}
		else{
			cb(response);
		}
	});
}

