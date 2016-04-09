//Import External Modules
var express = require('express');
var app = express();
var router = express.Router();
var bodyParser = require('body-parser');
var tokenCheckReference = new require("../../node_modules/token-auth-check/tokenCheck"); 
var tokenChecker = new tokenCheckReference();

//Configure Parser
router.use(bodyParser.json());
router.use(bodyParser.urlencoded({ extended: true }));

//Creating new activiti
router.post('/', function(req,res){
	tokenChecker.checkToken(req.body.userToken, cb);
	res.send("Needs to be authenticated");
});

//Update activiti info
router.put('/', function(req,res){
	res.send('PUT: Update Activiti info');
});

//Delete activiti
router.delete('/', function(req,res) {
	res.send('DELETE: Delete Activiti ');
});

//Return activiti info
router.get('/', function(req,res) {
	res.send('GET: Return Activiti ');
});

function cb(string) {
	console.log("In callback function and the response is : " + string);
}

module.exports = router; 
