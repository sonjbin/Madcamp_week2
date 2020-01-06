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
const imageSchema = require('../models/image').schema; 
var imageModel = mongoose.model('image', imageSchema);

/* GET gallery listing. */
router.get('/:id', function(req, res, next) {
  const iId = req.params.id || '';
	if (!iId.length) return res.status(400).json({error: 'Incorrenct id'});
	userModel.findOne({id: iId}, function(err, user){
		if(err) return res.status(404).json({error: 'Unknown user'});
		else return res.json(user.gallery);
	});
});

/* DELETE */
router.delete('/:id/:name', (req, res) => {
  const iId = req.params.id || '';
	if (!iId.length) return res.status(400).json({error: 'Incorrenct id'});
  const iName = req.params.name || '';
  if (!iName) return res.status(400).json({error: 'Incorrect name'});
	userModel.findOne({id: iId}, function(err, user){
		if(err) return res.status(404).json({error: 'Unknown user'});
		else{
			user.gallery = user.gallery.filter(function (n){
				return n.name != iName;
			});
			user.save();
			return res.status(204).send();
		}
	});
});

/* ADD image */
router.post('/:id', (req, res) => {
	const iGallery = JSON.parse(req.body.gallery) || [];
  const iId = req.params.id || '';
	if (!iId.length) return res.status(400).json({error: 'Incorrenct id'});
	userModel.findOne({id: iId}, function(err, user){
		if(err) return res.status(404).json({error: 'database failed'});
		if(!user) return res.status(404).json({error: 'Unknown user'});
		else{
			for(var i=0, nImage; nImage = iGallery[i]; i++){
				var check=0;
				for(var j=0, image; image = user.gallery[j]; j++){
					if(nImage.name == image.name) check += 1;
				}
				if(! check) user.gallery.push(nImage);
			}
			user.save();
			return res.status(201).json(user.gallery);
		}
	});
});

module.exports = router;
