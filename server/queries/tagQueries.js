//This is a middleware file that will create a tag
var pool = require('../node_modules/database/DBPool');
itself = require('./tagQueries');
//This function will create a tag and put it into the database
//First checks to see if the tag is actually present
//If it isnt then itll make a new one
exports.createTag = function(name, cb){
	itself.tagExistsName(name, function(response){
		if(response == null){
			var query = "INSERT INTO tags (tid, name) values (NULL, \'" + name + "\')";
			console.log(query);
			pool.sendQuery(query, function(res2){
				console.log("Db response is: " + JSON.stringify(res2));
				if(res2 == null){
					console.log("Error is: " + res2);
					cb(null);
				}
				else{
					console.log("insert id is : " + res2.insertId);
					cb(res2.insertId);
				}
			});
		}
		else{
			console.log("Response is: " + response);
			cb(response); // This is the TID
		}
	});
}

//This is a debug function that will be used to delete a tag
exports.deleteTag = function(tid, cb){
	var query = "delete from tags where tid = \'" + tid +"\'";
	pool.sendQuery(query, function(response,err){
		if(err){
			console.log(err);
			cb(null);
		}
		else{
			cb(response);
		}
	});
}

//This will modify the name of a tag based on the tid entered
//Must pass in a name
exports.modifyTag = function(tid, newName, cb){
	var query = "update tags set name = \'" + newName + "\' where tags.tid = " + tid;
	pool.sendQuery(query, function(response){
		if(response == null){
			cb(null);
		}
		else{
			cb(true);
		}
	});
}

//This is a function to see if a tag already exists
//Takes in a string for a name and a callback reference
//Either returns true or null
exports.tagExistsName = function(name, cb){
	searchName = name.toLowerCase();
	var query = "select * from tags where name = \'" + searchName + "\'";
	pool.sendQuery(query, function(response){
		if(response == null || response.length == 0){
			console.log("Tag does not already exist");
			cb(null);
		}
		else{ //Tag exists
			console.log(response.length);
			cb(response[0].tid);
		}
	});
}

//This is a function that will take in a TID and will return that tag's name
exports.getTagNameTID = function(tid, cb){
	var query = "select * from tags where tid = \'" + tid +"\'"
	pool.sendQuery(query, function(response,err){
		if(response.length == 0 || err){
			cb(null);
		}
		else{
			console.log(response[0].name);
			cb(response[0].name);
		}
	});
}








