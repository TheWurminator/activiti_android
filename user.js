//Init
var express = require('express');
var router = express.Router();


router.post('/', function(req,res){
	res.send('POST: Make new user');
});

router.put('/', function(req,res){
	res.send('PUT: Update user info');
});


router.delete('/', function(req,res) {
	res.send('DELETE: Delete User details');
});

router.get('/', function(req,res) {
	res.send('GET: Return user details');
});

module.exports = router; 

