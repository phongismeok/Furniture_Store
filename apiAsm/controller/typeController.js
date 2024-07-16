const Types = require('../models/typeModel')

exports.getAllType = async (req, res) => {
    try {
        const data = await Types.find();
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
    }
}