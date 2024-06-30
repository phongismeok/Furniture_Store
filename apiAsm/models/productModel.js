const mongoose = require('mongoose');
const Scheme = mongoose.Schema;

const Products = new Scheme({
        productId: {type: String, required: true},
        productName: {type: String, required: true},
        price: {type: Number, required: true},
        describe: {type: String, required: true},
        image1: {type: String, required: true},
        image2: {type: String, required: true},
        image3: {type: String, required: true},
        type: {type: Number, required: true},
        stateFavorites: {type: Number, required: true, default: 0},
        typeProduct: {type: String, required: true}
    }, {
        timestamps: false
    }
)
module.exports = mongoose.model('products', Products)
