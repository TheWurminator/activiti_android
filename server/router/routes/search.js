//Init
var express = require('express'); 
var router = express.Router(); 
var bodyParser = require('body-parser');
var app = express();
var jsonParser = bodyParser.json();

router.post('/activiti', jsonParser, function(req,res){
	for(x in req.body){
		console.log(req.body[x]);
	}
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
