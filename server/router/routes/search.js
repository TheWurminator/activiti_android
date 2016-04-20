//Init
var express = require('express'); 
var router = express.Router(); 
var bodyParser = require('body-parser');
var app = express();
var jsonParser = bodyParser.json();
var pool = require('../../node_modules/database/DBPool.js');

router.post('/activiti', jsonParser, function(req,res){
	var count = 0;
	var query = "select distinct atag.aid, a.name " + "from tags t " + "inner join activiti_tags atag on atag.tid = t.tid " + "inner join activitis a on a.aid = atag.aid " + "where ";
	for(x in req.body){
		console.log(req.body[x]);
		if(x == "max_cost"){
			query = query + "(a.cost > " + req.body['min_cost'] + ") and (a.cost < " + req.body['max_cost'] + ") and ";
		}
		else if(x == "name"){
			query = query + "( a.name like \'%" + req.body[x] +"%\' ) and ";
		}
		else if(x == "description"){
			query = query + "(a.description like \'%" + req.body[x] + "%\') and ";
		}
		else if(x == "tags"){
			console.log('In tags');
			for(y in req.body[x]){
				if(count == 0){
					query = query + "(t.name = \"" + req.body[x][y] + "\" ";
					count++;
				}
				else{
					query = query + "or t.name = \"" + req.body[x][y] +"\" ";
				}
			}
			query = query + ") and "
		}
		else if(x == "max_attendees"){
			query = query + "(a.max_attendees < " + req.body[x] +") and ";
		}
		else if(x == "min_attendees"){
			query = query + "(a.max_attendees > " + req.body[x] + ") and ";
		}
		else if(x == "start_date"){
			query = query + "(a.start_date >= \'" + req.body[x] + "\') and ";
		}
		else if(x == "end_date"){
			query = query + "(a.end_date <= \'" + req.body[x] + "\') and ";
		}
		else if(x == "max_distance"){
			console.log("Distance has yet to be implemented");
		}

	}
	query = query.slice(0, query.length-4);
	console.log(query);
	pool.sendQuery(query, function(response){
		query = null;
		if(response == null){
			res.status(400).send("Query not completed");
		}
		else{
			console.log(response);
			res.status(200).send(response);
		}
	});
});

module.exports = router;
