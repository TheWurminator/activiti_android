//Init
var express = require('express');
var router = express.Router();

router.post('/', function(req,res){
	res.send('POST: Create a message');
	var query = "";
});

//This may be used to update a message
router.put('/', function(req,res){
	res.send('PUT: not currently implemented');
	var query = "";
});

//This may be used ot delete a message
router.delete('/', function(req,res) {
	res.send('DELETE: Delete Message ');
	var query = "";
});

//This will be used to make facebook graph API calls on the user's behalf
router.get('/', function(req,res) {
	console.log(req.header.access_token);

});