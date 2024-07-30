const mongoose = require('mongoose');
const moment = require('moment');
const Scheme = mongoose.Schema;

const Order = new Scheme({
        productId: {type: String, required: true},
        productName: {type: String, required: true},
        image: {type: String, required: true},
        quantity: {type: Number, required: true},
        price: {type: Number, required: true},
        state: {type: String, required: true},
        account: {type: String, required: true},
    }, {
        timestamps: true
    }
)

Order.methods.toJSON = function () {
    const order = this;
    const orderObject = order.toObject();

    orderObject.createdAt = moment(orderObject.createdAt).format('YYYY-MM-DD HH:mm:ss');
    orderObject.updatedAt = moment(orderObject.updatedAt).format('YYYY-MM-DD HH:mm:ss');

    return orderObject;
};

module.exports = mongoose.model('orders', Order)