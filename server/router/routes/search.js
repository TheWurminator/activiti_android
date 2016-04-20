//Init
var express = require('express'); 
var router = express.Router(); 
var bodyParser = require('body-parser');
var app = express();
var jsonParser = bodyParser.json();

router.post('/activiti', jsonParser, function(req,res){
	var count = 0;
	var query = "select atag.aid, a.name " + "from tags t " + "inner join activiti_tags atag on atag.aid = t.tid " + "inner join activitis a on a.aid = atag.aid " + "where ";
	for(x in req.body){
		console.log(req.body[x]);
		if(x == "max_cost"){
			query = query + "a.cost > " + req.body['min_cost'] + " and a.cost < " + req.body['max_cost'] + " and ";
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
			query = query + "a.max_attendees < " + req.body[x] +" and ";
		}
		else if(x == "min_attendees"){
			query = query + "a.max_attendees > " + req.body[x] + " and ";
		}
		else if(x == "start_date"){
			query = query + "a.start_date >= \'" + req.body[x] + "\' and ";
		}
		else if(x == "end_date"){
			query = query + "a.end_date <= \'" + req.body[x] + "\' and ";
		}
		else if(x == "max_distance"){
			console.log("Distance has yet to be implemented");
		}

	}
	console.log(query);
	query = null;
	/*
	SELECT atag.aid, a.name
	FROM tags t
	INNER JOIN activiti_tags atag ON atag.tid = t.tid
	INNER JOIN activitis a ON a.aid = atag.aid
	WHERE t.name =  "fun times"
	OR t.name =  "horror"

	*/
	res.sendStatus(200);
});

//Functionality not set
router.put('/', function(req,res){
	res.send('Not Implemented');
	var query = "";
});

//Functionality not set
router.delete('/', function(req,res) {
	res.send('Not Implemented');
	var query = "";
});

//Functionality not set
router.get('/', function(req,res) {
	res.send('Send search filter');
	var query = "";
});

module.exports = router;
