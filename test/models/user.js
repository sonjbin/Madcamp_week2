const mongoose = require ('mongoose');
const contactSchema = require('./contact').schema;
const imageSchema = require('./image').schema;
const Schema = mongoose.Schema;
/**
 * Create Schema
 * https://stackoverflow.com/questions/43024285/embedding-schemas-is-giving-error/43024503
 */
const userSchema = new Schema({
    id: {  // pending encryption
        type: String,
        unique: true
    },
    contacts: [{
        type: contactSchema
    }],
    gallery: [{
        type: imageSchema
    }]
});
module.exports = mongoose.model ('user', userSchema);
