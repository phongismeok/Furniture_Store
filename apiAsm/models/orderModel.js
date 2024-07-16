const mongoose = require('mongoose');
const Scheme = mongoose.Schema;

const Order = new Scheme({
        productId: {type: String, required: true},
        quantity: {type: Number, required: true},
        state: {type: String, required: true},
        account: {type: String, required: true},
    }, {
        timestamps: false
    }
)
module.exports = mongoose.model('orders', Order)