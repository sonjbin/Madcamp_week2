var Client = require('mongodb').MongoClient;
var db;
Client.connect('mongodb://localhost:27017/madcamp2', function(error, database){
    if(error) {
        console.log(error);
    } else {
        
        db = database.db('madcamp2')
        console.log("connected:"+db.databaseName);
        // var michael = {name:'AA', number: 2};
        
        db.collection('contact').insertMany([{name:'AA', number: 2},{name:'AA', number: 2},{name:'AA', number: 2},{name:'AA', number: 2},{name:'AA', number: 2}]);


        database.close();
    }
});
