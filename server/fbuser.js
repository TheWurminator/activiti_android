//This is a user class that is made to hold the user's token 
//And the randomly generated token

//
function fbUser(token) {
	this.fb_token = token; //User's Facebook token
	//TODO: Randomly generate the token
	//QUESTION: Should we make a new generator in each user class? Does it matter? 
	this.act_token = null; //User's ActivitI token
}

//Function to generate a unique token for a user
fbUser.prototype.generateToken = function(){
	//TODO: Generate a unique token for each user
	//Hint: Use randomtoken npm package
	//this.act_token = ???
}

module.exports = fbUser; //Exporting the instantiation of the class