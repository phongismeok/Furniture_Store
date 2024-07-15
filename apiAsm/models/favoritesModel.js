const mongoose = require('mongoose');
const Scheme = mongoose.Schema;

const Favorites = new Scheme({
        productId: {type: String, required: true},
        productName: {type: String, required: true},
        image: {type: String, required: true},
        price: {type: Number, required: true},
        account: {type: String, required: true}
    }, {
        timestamps: false
    }
)
module.exports = mongoose.model('favorites', Favorites)