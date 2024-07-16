const mongoose = require('mongoose');
const Scheme = mongoose.Schema;

const Notification = new Scheme({
        title: {type: String, required: true},
        content: {type: String, required: true},
        state: {type: Number, required: true},
        image: {type: String, required: true},
        account: {type: String, required: true},
    }, {
        timestamps: false
    }
)
module.exports = mongoose.model('notifications', Notification)