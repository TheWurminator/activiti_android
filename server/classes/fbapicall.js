this.graph = require('fbgraph');

exports.getUserInfo = function(fbtoken,cb){
	var facebookParameters = "fields=first_name, last_name, birthday, gender&"
	this.graph
	  .setAccessToken(this.getFacebookToken())
	  .get("/me?" + facebookParameters, function(err, data) {
	      cb(data); //Sent back as JSON
	  });
}