function connectDB()
{
    mongoClient.connect(databaseURL,
        function (err, db)
        {
            if (err)
            {
                console.log('db connect error');
                return;
            }
 
            console.log('db was connected : ' + databaseURL);
            database = db;
    );
 
}
