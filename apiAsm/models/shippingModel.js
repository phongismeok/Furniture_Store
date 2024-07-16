const mongoose = require('mongoose');
const Scheme = mongoose.Schema;

const Shipping = new Scheme({
        name: {type: String, required: true},
        address: {type: String, required: true},
        addressDetail: {type: String, required: true},
        account: {type: String, required: true},
        select: {type: Number, required: true}
    }, {
        timestamps: false
    }
)
module.exports = mongoose.model('shippings', Shipping)