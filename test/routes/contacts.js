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
var userModel = mongoose.model('user', userSchema);
const contactSchema = require('../models/contact').schema; 
var contactModel = mongoose.model('contact', contactSchema);

/* GET contacts listing. */
router.get('/:id', function(req, res, next) {
  const iId = req.params.id || '';
	if (!iId.length) return res.status(400).json({error: 'Incorrenct id'});
	userModel.findOne({id: iId}, function(err, user){
		if(err) return res.status(400).json({error: 'database failed'});
		if(!user) return res.status(404).json({error: 'Unknown user'});
		else return res.json(user.contacts || '');
	});
});

/* DELETE */
router.delete('/:id/:number', (req, res) => {
  const iId = req.params.id || '';
	if (!iId.length) return res.status(400).json({error: 'Incorrenct id'});
  const iNumber = req.params.number || '';
  if (!iNumber) return res.status(400).json({error: 'Incorrect number'});
	userModel.findOne({id: iId}, function(err, user){
		if(err) return res.status(404).json({error: 'Unknown user'});
		if(!user) return res.status(404).json({error: 'Unknown user'});
		else{
			user.contacts = user.contacts.filter(function (n){
				return n.number != iNumber;
			});
			user.save();
			return res.status(204).send();
		}
	});
});

/* ADD contact */
router.post('/:id', (req, res) => {
	const iContacts = JSON.parse(req.body.contacts) || [];
  const iId = req.params.id || '';
	if (!iId.length) return res.status(400).json({error: 'Incorrenct id'});
	userModel.findOne({id: iId}, function(err, user){
		if(!user) user = new userModel();
		if(err) return res.status(404).json({error: 'database failed'});
		else{
			console.log("in else");
			for(var i=0, nContact; nContact = iContacts[i]; i++){
				var check=0;
				for(var j=0, contact; contact = user.contacts[j]; j++){
					if(nContact.number == contact.number) check += 1;
				}
				if(! check) user.contacts.push(nContact);
			}
			user.save();
			return res.status(201).json(user.contacts || '');
		}
	});
});

module.exports = router;
