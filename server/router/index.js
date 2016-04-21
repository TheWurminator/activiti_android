//Initialize and Export Routes
module.exports = function (app) {
	//Verify valid token
	function auth(req,res,next){
		var tokenChecker = new require('../node_modules/token-auth-check/tokenCheck');
		var token = req.get('token');
		tokenChecker.checkToken(token, function(response){
			if(Boolean(response) === false){
				res.sendStatus(401);
			}
			else{
				next();
			}
		});
	}

	//Facebook Routes - Used to authenticate with facebook server
	app.use('/api/login/facebook', require('./routes/facebook'));

	//Application Routes - From Web-URL redirect to specified file for processing
	app.use('/api/user', auth, require('./routes/user'));
	app.use('/api/activiti', auth, require('./routes/activiti')); 
	app.use('/api/search', auth, require('./routes/search')); 
	app.use('/api/messaging', require('./routes/messaging'));
	app.use('/api/tags', auth, require('./routes/tags'));
	
	//Wildcard Route - Catch all failed routes
	app.use('/*', function(req,res){
		res.sendStatus(404);
	});
};
