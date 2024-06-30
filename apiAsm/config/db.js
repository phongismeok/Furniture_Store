const mongoose = require('mongoose');

const local = "mongodb+srv://phong:7An8OTOQ49as30Ra@phong.lvayrsx.mongodb.net/AsmKotlin_MVVM";

const connect = async () => {
    try {
        await mongoose.connect(local);
        console.log('Connect success');
    } catch (error) {
        console.error('Connection to MongoDB failed:', error);
    }
}

module.exports = { connect };
