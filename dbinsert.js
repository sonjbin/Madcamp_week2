var Client = require('mongodb').MongoClient;

Client.connect('mongodb://localhost:27017/madcamp2', function(error, db){
    if(error) {
        console.log(error);
    } else {
        // 1. 입력할 document 생성
        var michael = {name:'AA', number: 2};
        // 2. student 컬렉션의 insert( ) 함수에 입력
        db.collection('contact').insert(michael);

        db.close();
    }
});