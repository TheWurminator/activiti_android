//Init
var express = require('express'); //Need the express module
var router = express.Router(); //Need the router to be able to catch http requests
//Will be used to send search parameters and will send back the results in a JSON format
router.post('/', function(req,res){
	res.send('Not Implemented');
	var query = "";
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
