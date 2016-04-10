var thing = require('./userQueries');

var token = "rFVVl192u1dpm0bq4G6OrCtTWiw8Bc8w1Uxc36BYnoCLDpTGbe0b1rfOuY8MFkPNLOSpTjVgZWabSiCcenkuIjMtDfE7CU9VQUH5l3EZ4dYPXgUT3OqFB5mwj1XYUA7J21uTim7Fidp5ITOiZ0AGIkwRQNeoApNj2zyEBMq81yGJ0d7cRKTf5bLIHw2jAn8iEu8NPn10nWVmSpp6EkpyTTe7grf6yvk50UABmS9ABOgPC8qHaVKDCsMCK9adU2y";

thing.updateProfile(token, null, function(response){
	console.log("haha");
})