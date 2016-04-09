this.graph = require('fbgraph');

exports.getUserInfo = function(fbtoken,cb){
	var facebookParameters = "fields=first_name, last_name, birthday, gender&"
	this.graph
	  .setAccessToken(fbtoken)
	  .get("/me?" + facebookParameters, function(err, response) {
	      cb(response); //Sent back as JSON
	  });
}