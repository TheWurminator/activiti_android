//Init
var express = require('express');
var app = express();
var router = express.Router();
var bodyParser = require('body-parser');
router.use(bodyParser.urlencoded({ extended: true }));
router.use(bodyParser.json());
var checkRef = new require("../../node_modules/token-auth-check/tokenCheck"); 
var checker = new checkRef();

//Creating a new activiti
router.post('/', function(req,res){
	checker.checkToken(req.body.userToken, cb);
	res.send("Needs to be authenticated");
});

function cb(string) {
	console.log("In callback function and the response is : " + string);
}

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
