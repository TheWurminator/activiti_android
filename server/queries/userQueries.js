this.pool = require('../node_modules/database/DBPool');

exports.createUser = function(fbtoken, uid){
	
}

exports.deleteUser = function(uid,cb){
	var query = "delete from users where \'users\'.\'uid\' = " + uid;
	this.pool.sendQuery(query, function(response){
		cb(response);
	}); 
}

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