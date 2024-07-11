const mongoose = require('mongoose');
const Scheme = mongoose.Schema;

const Users = new Scheme({
        username: {type: String, required: true},
        password: {type: String, required: true},
        avatar: {type: String, required: false},
        name: {type: String, required: true}
    }, {
        timestamps: false
    }
)
module.exports = mongoose.model('users', Users)

