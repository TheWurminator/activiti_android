//Init
var express = require('express');
var router = express.Router();

router.post('/', function(req,res){
	res.send('POST: Make new user');
	var query = "";
});

router.put('/', function(req,res){
	res.send('PUT: Update user info');
	var query = "";
});


router.delete('/', function(req,res) {
	res.send('DELETE: Delete User details');
	var query = "";
});

router.get('/', function(req,res) {
	res.send('GET: Return user details');
	var query = "";
});

module.exports = router; 
