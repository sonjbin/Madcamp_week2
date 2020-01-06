var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var diarySchema = new Schema({
	date: String,
	photo: Buffer
});

module.exports = mongoose.model('diary', diarySchema);
