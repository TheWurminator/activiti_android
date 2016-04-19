this.pool = require('../node_modules/database/DBPool');
this.tagQueries = require('./tagQueries');
//Makes a new user based on FB graph response
exports.createActiviti = function(info, userToken, cb){
	var addQuery = "insert into activities (aid, name, description, cost, max_attendees, start_date, end_date, start_time, end_time, latitude, longitude, uid) values (\'null\', + \'" + info.name + "\', \'" + info.cost + "\', \'" + info.description + "\', \'" + info.max_attendees + "\', \'" + info.start_date + "\', \'" + info.end_date + "\', \'" + info.start_time + "\', \'" + info.end_time + "\', \'" + info.latitude + "\', \'" + info.longitude + "\', \'" + userToken + "\');"
	this.pool.sendQuery(addQuery, function(response, err){
		if(err){
			console.log(err);
			cb(null);
		}
		else{
			cb(response);
		}
	});
};

//Deletes a activiti
exports.deleteActiviti = function(aid, cb){
	var query = "delete from activities where activities.aid = \'" + aid + "\'";
	this.pool.sendQuery(query, function(response){
		if(err){
			cb(null);
		}
		else{
			cb(response);
		}
	}); 
};

//Checks to see if Activiti exists
exports.activitiExists = function(uid, cb){
	var query = "";

	this.pool.sendQuery(query, function(response){
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
	var query = "select * from activities where aid = \'" + aid + "\'";
	this.pool.sendQuery(query, function(response, err){
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
	var query = "select * from activities";
	this.pool.sendQuery(query, function(response,err){
		if(err){
			console.log(err);
		}
		else{
			console.log("Activiti successfully updated");
		}
	});
}

//This is a function that will set the tags for a specific activiti
//Taked in an aid and json w/tags
exports.setTagsActiviti = function(activiti_id, tags, cb){
	for (x in tags){
		this.tagQueries.tagExistsName(x, function(tagTID){
			if(tagTID == null){
				//Need to make a new tag
				this.tagQueries.createTag(x, function(response){
					if(response == null){
						console.log("Tag wasn't created");
						//Tag wasn't created
					}
					else{
						//Tag was created

					}
				});
			}
			else{ //Tag does exist
				var query = "insert into activiti_tags (tid,aid) values (" + tagTID + ", " + activiti_id+")";
				this.pool.sendQuery(query, function(response,err){
					if(err){
						console.log(err);
					}
					else{
						console.log(response);
					}
				});
				
			}
		});
	}
}
