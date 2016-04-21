//Import Modules
var express = require('express'); 
var router = express.Router();
var activitiQueries = require('../../queries/activitiQueries');
var tagQueries = require('../../queries/tagQueries');
var userQueries = require('../../queries/userQueries');
var bodyParser = require('body-parser');
var jsonParser = bodyParser.json();

//Creating new activiti
router.post('/', jsonParser, function(req,res) {
	//REPLACE ACTIVITIINFO WITH SPECS FROM BODY -----
	var activitiInfo = req.body;
	userQueries.getUIDfromToken(req.get('token'), function(response){
		if(response !== null){
			activitiQueries.createActiviti(activitiInfo, response, function(res2) {
				if(res2 == null){
					res.status(400).send("Unable to create Activiti");
				}
				else{
					res.status(201).send("Activiti Created.");
				}
			});
		}
	});
})
;
router.post('/tags', jsonParser, function(req,res){
	activitiQueries.setTags(req.get('aid'), req.body.tags, function(response){
		if(response == null){
			res.status(400).send("Doesn't work");
		}
		else{
			res.status(200).send("tags successfully added to activiti");
		}
	});
});

//Update activiti info
router.put('/', jsonParser, function(req, res) {
	//REPLACE WITH INFO FROM BODY ----
	var aid = req.get('aid'); //Activiti ID to update
	activitiQueries.updateActiviti(aid, req.body, function(response){
		console.log("response is: " + response);
		if(response == null){
			res.status(400).send("Activiti update has failed");
		}
		else{
			res.status(200).send("Activiti successfully updated");
		}
	});
});

//Delete activiti
router.delete('/', jsonParser, function(req,res) {
	var aid = req.get('aid');
	console.log("Aid to get deleted: " + aid);
	userQueries.getUIDfromToken(req.get('token'), function(response){
		if(response == null){
			res.status(401).send("Unauthorized to modify activiti");
		}
		else{
			activitiQueries.deleteActiviti(aid, response, function(response){
				if(response == null){
					res.status(400).send("Deletion unsuccessful");
				}
				else{
					res.status(200).send("Deletion successful");
				}
			});
		}
	});
});

//Return activiti info
router.get('/', jsonParser, function(req,res) {
	//REPLACE WITH ACTIVITI ID TO BE RETRIEVED	
	var aid = req.get('aid');
	activitiQueries.getActiviti(aid, function(response) {
		if(response == null){
			res.status(400).send("Activiti not found");
		}
		else{
			res.status(200).send(response);
		}
	});
});

//This will return all of the tags for an activiti
router.get('/tags', jsonParser, function(req,res){
	activitiQueries.getTagsActiviti(req.get("aid"), function(response){
		if(response == null){
			res.sendStatus(400);
		}
		else{
			res.status(200).send(response);
		}
	});
});



module.exports = router; 
