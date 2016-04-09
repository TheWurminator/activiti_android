//Initialize and Export Routes
module.exports = function (app) {
	//Verify valid token
	var auth = function IsAuthenticated(req,res,next){
		var tokenChecker = require('../node_modules/token-auth-check/tokenCheck');
		var token = req.get('token');

		if(tokenChecker.checkToken(token)) 
        	next();
		else 
			res.send(403);
	};
		
	//Application Routes - From Web-URL redirect to specified file for processing
	app.use('/api/user', auth, require('./routes/user'));
	app.use('/api/activiti', auth, require('./routes/activiti')); 
	app.use('/api/search', auth, require('./routes/search')); 
	app.use('/api/messaging', auth, require('./routes/messaging'));

	//Facebook Routes - Used to authenticate with facebook server
	app.use('/api/login/facebook', require('./routes/facebook'));
	
	//Wildcard Route - Catch all failed routes
	app.use('/*', function(req,res){
		res.sendStatus(404);
	});
};
