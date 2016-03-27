var express = require('express');
var app = express();
var bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({ extended: true}));
app.use(bodyParser.json());

var port = process.env.PORT || 8000;

var router = express.Router();

router.get('/', function(req, res){
	res.json({ message: 'eokfpowekf'});
	console.log('a person accessed this' );
});

app.use('/api', router);

app.listen(port);
console.log('this is happening at port ' + port);