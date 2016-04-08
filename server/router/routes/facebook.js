//Init
var express = require('express');
var router = express.Router();

//Redirect User to facebook
router.post('/', function(req,res){
	passport.authenticate('facebook');
});

router.put('/', function(req,res){
});

router.delete('/', function(req,res) {
});

router.get('/', function(req,res) {
	
});

// Exporting the functionality of the router to the calling module
module.exports = router; 
