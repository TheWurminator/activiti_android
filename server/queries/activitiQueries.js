this.pool = require('../node_modules/database/DBPool');

//Makes a new user based on FB graph response
exports.createActiviti = function(info, userToken, cb){
	var addQuery = "insert into activities (aid, name, cost, max_attendees, date, start_time, end_time, longitude, latitude) values (\'null\', + \'" + info.name + "\', \'" + info.cost + "\', \'" + info.max_attendees + "\', \'" + info.date + "\', \'" + info.start_time + "\', \'" + info.end_time + "\', \'" + info.longitude + "\', \'" + info.latitude + "\');"
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
};
