//Import Modules
var express = require('express'); 
var router = express.Router();
var activitiQueries = require('../../queries/activitiQueries');
var bodyParser = require('body-parser');
var jsonParser = bodyParser.json();

//Creating new activiti
router.post('/', function(req,res) {
	
	//REPLACE ACTIVITIINFO WITH SPECS FROM BODY -----
	var activitiInfo = req.get('stuff');

	activitiQueries.createActiviti(activitiInfo, req.get('token'), function(response) {
		if(response == null){
			res.status(400).send("Unable to create Activiti");
		}
		else{
			res.status(200).send("Activiti Created.");
		}
	});
});

//Update activiti info
router.put('/', function(req, res) {
	//REPLACE WITH INFO FROM BODY ----
	var aid = req.get('aid'); //Activiti ID to update

	activitiQueries.updateActiviti(aid, req.body, function(response) {
		if(response == null){
			res.status(400).send("Activiti not updated");
		}
		else{
			res.status(200).send("Activiti updated");
		}
	});
});

//Delete activiti
router.delete('/', function(req,res) {
	//REPLACE WITH ACTIVITI ID TO BE DELETED
	var aid = req.get('aid');

	activitiQueries.deleteActiviti(aid, function(response){
		if(response == null || response.affectedRows == 0){
			res.status(400).send("Unable to delete activiti, activiti not found");
		}
		else{
			res.status(200).send("activiti deleted");
		}
	});
});

//Return activiti info
router.get('/', function(req,res) {
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

module.exports = router; 
