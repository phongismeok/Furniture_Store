const mongoose = require('mongoose');
const Scheme = mongoose.Schema;

const Types = new Scheme({
        name: {type: String, required: true},
        image: {type: String, required: true},
    }, {
        timestamps: false
    }
)
module.exports = mongoose.model('types', Types)