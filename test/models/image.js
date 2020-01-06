var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var imageSchema = new Schema({
	name: {
		type: String,
	},
	photo: {
		type: String
	}
});

module.exports = mongoose.model('image', imageSchema);
