//Import Modules
var express = require('express'); 
var router = express.Router();
var tagQueries = require('../../queries/tagQueries');
var bodyParser = require('body-parser');
var jsonParser = bodyParser.json();

//This is the function to create a tag
router.post('/', jsonParser, function(req,res) {
	var name = req.body.tagname.toLowerCase(); //Converts the tag to lowercase
	console.log(name); //Print the name out
	var localRef = this;
	tagQueries.tagExistsName(name, function(response){
		if(response == null){
			//Make the tag
			tagQueries.createTag(name, function(thisResponse){
				if(thisResponse == null){
					res.status(400).send("There was an error creating a tag");
				}
				else{
					res.status(200).send("Tag successfully created");
				}
			});
		}
		else{
			res.status(200).send("Tag already exists");
		}
	});
});

//DEBUG
//This function is used to delete a tag from the database
//To use, put a field "tid" in the header
router.delete('/', jsonParser, function(req,res){
	var tid = req.get('tid');
	tagQueries.deleteTag(tid, function(response){
		if(response != null){
			res.status(200).send("Tag successfully deleted");
		}
		else{
			res.status(400).send("Error when deleting tag");
		}
	})
});

//DEBUG
//This function is used to edit the name of a tag in the db
//This is based on a TID that is given
router.put('/', jsonParser, function(req,res){
	var tid = req.get('tid');
	var newName = req.get('newName');
	tagQueries.modifyTag(tid, newName, function(response){
		if(response == null){
			res.status(400).send("Tag modification error");
		}
		else{
			res.status(200).send("Tag successfully modified");
		}
	});
});

//DEBUG
//This is a function that will fetch a tag based on tid and return it to the user
router.get('/', jsonParser, function(req,res){
	tagQueries.getTagNameTID(req.get('tid').toLowerCase(), function(response){
		console.log(response);
		if(response != null){
			res.status(200).send(response);
		}
		else{
			res.status(400).send("Tag not found based on tid");
		}
	});
});

module.exports = router;