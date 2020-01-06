var express = require('express');
var router = express.Router();
var bodyParser = require('body-parser');
router.use(bodyParser.json());
router.use(bodyParser.urlencoded({ extended: true }));

var mongoose = require ('mongoose')
mongoose.connect("mongodb://localhost:27017/test");
var db = mongoose.connection;
db.on('error', ()=>console.log('connection failed'));
db.once('open', ()=>console.log('connected'));

const userSchema = require('../models/user').schema; 
var user = mongoose.model('user', userSchema);

/* GET users listing. */
router.get('/', function(req, res, next) {
	user.find(function(err, users){
		if(err) return res.status(500).json({error: err});
		else return res.json(users);
	});
});

/* GET*/ 
router.get('/:id',(req, res)=>{
  const iId = req.params.id || '';
	if (!iId) return res.status(400).json({error: 'Incorrect id'});
	user.findOne({id: iId}, function(err, user){
		if(err) return res.status(404).json({error: 'Unknown user'});
		else return res.json(user);
	});
});

/* DELETE */
router.delete('/:id', (req, res) => {
  const iId = req.params.id || '';
  if (!iId) return res.status(400).json({error: 'Incorrect id'});
	user.remove({id: iId}, function(err, output){
		if(err) return res.status(500).json({error: 'database failed'});
		if(output.n==0) return res.status(404).json({error: 'user not found'})
		res.status(204).end();
	});
});

/* ADD user */
router.post('/', (req, res) => {
	console.log("id", req.body.id);
  const iId = req.body.id || '';
	if (!iId.length) return res.status(400).json({error: 'Incorrenct id'});
	user.findOne({id: iId}, function(err, users){
		if(err) res.status(400).json({error: 'Incorrenct id'});
		else {
			if(users != null) return res.status(400).json({error: 'already exists'});
			else{
				var newUser = new user({id: iId});
				newUser.save(function(err, data){
					if(err) {
						console.log(err);
						return res.status(500).json({error: 'database failed'});
						}
						else return res.status(201).json(newUser);
				});
			}
		}
	});
});

module.exports = router;
