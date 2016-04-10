this.pool = require('../node_modules/database/DBPool');

exports.search = function(search_json, cb){
	//Location, date, tags, name
	/*
	{
		"location": "54.00003, 23.32323",
		"date": "07/17/56",
		"tags":["pokemon", "movie"],
		"name": "fun"
	}
	*/
	var query = "select * from activities where date = \'" + date + "\' "
	console.log(search_json);
}

//Do we need functions for various search parameters


