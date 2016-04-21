//Init
var express = require('express'); 
var router = express.Router(); 
var bodyParser = require('body-parser');
var app = express();
var jsonParser = bodyParser.json();
var pool = require('../../node_modules/database/DBPool.js');

router.post('/activiti', jsonParser, function(req,res){
	var count = 0;
	var query = "select distinct atag.aid, a.latitude, a.longitude, a.name, a.description " + "from tags t " + "inner join activiti_tags atag on atag.tid = t.tid " + "inner join activitis a on a.aid = atag.aid " + "where ";
	var length = query.length;
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
	}
	if(query.length > length){
		var labels = JSON.parse('["aid","name","latitude","longitude"]');
		query = query.slice(0, query.length-4);
		console.log(query);
		pool.sendQuery(query, function(response){
			//console.log(response);
			query = null;
			if(response == null){ //Response did not work
				res.status(400).send("Query not completed");
			}
			else{
				if(req.body['max_distance']){ //If there is a distance parameter
					var finalResponse = [];
					for(z in response){
						var hcalc = haversine(response[z]['latitude'], response[z]['longitude'], req.body['latitude'], req.body['longitude']);
						if( hcalc < req.body['max_distance']){
							finalResponse.push(response[z]);
						}
					}
					res.status(200).send(finalResponse);
				}
				else{ //If there isn't a distance parameter
					res.status(200).send(response);
				}
			}
		});
	}
	else{ //They didn't send anything
		res.status(200).send("No search parameters were sent");
	}
	
});

//This is a function that converts a number from degrees to radians
function toRad(x){
	return x * Math.PI/180;
}

//Performs the haversine distance formula and returns the distance
function haversine(alat, alng, usrlat, usrlng){
	var R = 3961; //km
	var x1 = usrlat - alat;
	var dLat = toRad(toRad(x1));
	var x2 = usrlng-alng;
	var dLon = toRad(x2);
	var a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(toRad(alat)) * Math.cos(toRad(usrlat)) * Math.sin(dLon/2) * Math.sin(dLon/2);
	var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	var d = R * c;
	return d;
}

module.exports = router;
