//Init
var express = require('express');
var router = express.Router();

router.post('/', function(req,res){
	res.send('POST: Create a message');
});

router.put('/', function(req,res){
	res.send('PUT: not currently implemented');
});

router.delete('/', function(req,res) {
	res.send('DELETE: Delete Message ');
});

router.get('/', function(req,res) {
	res.send('GET: Return MSG ');
});


module.exports = router; 

