const mongoose = require('mongoose');
const Scheme = mongoose.Schema;

const Carts = new Scheme({
        productId: {type: String, required: true},
        quantity: {type: Number, required: true},
    }, {
        timestamps: false
    }
)
module.exports = mongoose.model('carts', Carts)




