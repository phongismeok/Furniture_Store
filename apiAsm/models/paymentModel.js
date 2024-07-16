const mongoose = require('mongoose');
const Scheme = mongoose.Schema;

const Payment = new Scheme({
        numberCard: {type: String, required: true},
        select: {type: Number, required: true},
        account: {type: String, required: true},
    }, {
        timestamps: false
    }
)
module.exports = mongoose.model('payments', Payment)