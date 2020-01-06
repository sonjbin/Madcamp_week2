const mongoose = require('mongoose')
const Schema = mongoose.Schema

var foodSchema = new Schema({
    name: {
        type: String
    },
    ingredients: [{
        type: String
    }]
})