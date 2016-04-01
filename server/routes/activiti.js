//Init
var express = require('express');
var router = express.Router();

router.post('/', function(req,res){
	res.send('POST: Make new Activiti');
});

router.put('/', function(req,res){
	res.send('PUT: Update Activiti info');
});


router.delete('/', function(req,res) {
	res.send('DELETE: Delete Activiti ');
});

router.get('/', function(req,res) {
	res.send('GET: Return Activiti ');
});

module.exports = router; 
