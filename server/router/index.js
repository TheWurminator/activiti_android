//Initialize and Export Routes
module.exports = function (app) {
	//Application Routes - From Web-URL redirect to specified file for processing
	// app.use('/api/user', require('./routes/user'));
	app.use('/api/activiti', require('./routes/activiti')); 
	// app.use('/api/search', require('./routes/search')); 

	//Facebook Routes - Used to authenticate with facebook server
	app.use('/api/login/facebook', require('./routes/facebook'));
	
	//Wildcard Route - Catch all failed routes
	app.use('/*', function(req,res){
		res.sendStatus(404);
	})
};
