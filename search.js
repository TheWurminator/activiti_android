//Init
var express = require('express');
var router = express.Router();

router.post('/', function(req,res){
	res.send('Not Implemented');
});

router.put('/', function(req,res){
	res.send('Not Implemented');
});

router.delete('/', function(req,res) {
	res.send('Not Implemented');
});

router.get('/', function(req,res) {
	res.send('Send search filter');
});

module.exports = router; 

