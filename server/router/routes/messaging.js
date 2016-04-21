//Init
var express = require('express');
var router = express.Router();


//This will be used to send a message to the group chat
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

//This will be used to receive new messages from the group chat
router.get('/', function(req,res) {
	var query = "select * from users";
	res.status(200).send("Daniel knows what he's doing");
});

//Function to send the query
//THIS IS THE CALLBACK
function queryResponse(thing, res) {
	res.send(thing);
}

// Exporting the functionality of the router to the calling module
module.exports = router; 
