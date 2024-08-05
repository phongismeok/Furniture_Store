const mongoose = require('mongoose');
const Scheme = mongoose.Schema;

const Comment = new Scheme({
        productId: {type: String, required: true},
        content: {type: String, required: true},
        rate: {type: Number, required: true},
        account: {type: String, required: true},
        avatar : {type: String, required: true}
    }, {
        timestamps: false
    }
)
module.exports = mongoose.model('comments', Comment)