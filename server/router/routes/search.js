//Init
var express = require('express'); 
var router = express.Router(); 

//Used to send search parameters and send back the results in a JSON format
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
