

var mysql = require("mysql");
var con = mysql.createConnection({
	host: "localhost",
	user: "root",
	password: "abc123",
	database: "testingnodeusers"
});

con.connect(function(err){
	if(err){
		console.log('Error connecting to DB');
		return;
	}
	console.log('Connection Established');
});

// con.query('SELECT * FROM emp', function(err,rows){
// 	if(err){
// 		console.log('ewoifjewoifjewjf');
// 		throw err;
// 	}
// 	console.log('Data received from Db:\n');
//   	console.log(rows);

// });
var makeEmployee = function(nam, loc){
	var employee = {name : nam, location: loc};
	insertIntoDatabase(employee);
}

var insertIntoDatabase = function(emp){
	con.query('INSERT INTO emp SET ?', emp, function(err,res){
		if(err) throw err;
		console.log('Last insert ID:', res.insertID);
	});
}
var endConnection = function(){
	//This is just a function that ends the connection to the server
	con.end(function(err){
		if(err)throw err;
		console.log('The connection to the server has been terminated');
	});
}

var searchDatabaseByName = function(name){
	console.log(name);
	var query = "select * from emp where name = '" + name + "'";
	console.log(query);
	con.query(query, function(err,entry){

		if(err)throw err;
		console.log('Data received from Db:\n');
		console.log(entry);
	});
}

// exports.insertIntoDatabase = insertIntoDatabase;
exports.makeEmployee = makeEmployee;
exports.endConnection = endConnection;
exports.searchName = searchDatabaseByName;

