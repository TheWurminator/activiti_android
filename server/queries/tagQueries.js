//This is a middleware file that will create a tag
this.pool = require('../node_modules/database/DBPool');

//This function will create a tag and put it into the database
exports.createTag = function(name, cb){
	var query = "INSERT INTO tags (tid, name) values (NULL, \'" + name + "\')";
	this.pool.sendQuery(query, function(response,err){
		if(err){
			console.log(err);
			cb(null);
		}
		else{
			cb(response);
		}
	});
}

//This is a debug function that will be used to delete a tag
exports.deleteTag = function(tid, cb){
	var query = "delete from tags where tid = \'" + tid +"\'";
	this.pool.sendQuery(query, function(response,err){
		if(err){
			console.log(err);
			cb(null);
		}
		else{
			cb(response);
		}
	});
}

//This is a function to see if a tag already exists
//Takes in a string for a name and a callback reference
//Either returns true or null
exports.tagExistsName = function(name, cb){
	searchName = name.toLowerCase();
	var query = "select * from tags where name = \'" + searchName + "\'";
	this.pool.sendQuery(query, function(response,err){
		if(err){
			console.log(err);
			cb(null);
		}
		else{
			cb(True);
		}
	})
}

//This is a function that will take in a TID and will return that tag's name
exports.getTagNameTID = function(tid, cb){
	var query = "select * from tags where tid = \'" + tid +"\'"
	this.pool.sendQuery(query, function(response,err){
		if(err){
			//console.log(err);
			cb(null);
		}
		else{
			console.log(response[0].name);
			cb(response[0].name);
		}
	});
}

//This is a function that will fetch the tags for an activiti
//Takes in an activiti id, and returns a json with tags
exports.getTagsActiviti = function(activiti_id, cb){
	var query = "";
}

//This is a function that will fetch the tags for a specific user
//Takes in a user_token and returns a json with tags
exports.getTagsUser = function(usertoken, cb){
	var query = ""; //INNER JOIN
}

//This is a function that will set the tags for a specific activiti
//Taked in an aid and json w/tags
//Returns 200 OK or 400 error
exports.setTagsActiviti = function(activiti_id, tags, cb){
	for (x in tags){
		var query = "insert into tags (tid, aid) values (null, \'" + x + "\')";

	}

}

//This is a function that will set the tags for the user
//Takes in a uid and json w/tags
//Returns 200 OK or 400 ERROR
exports.setTagsUser = function(uid, tags, cb){
	var query = "";

}
