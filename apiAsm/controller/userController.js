const Users = require('../models/userModel')

exports.getAllUser = async (req, res) => {
    try {
        const data = await Users.find();
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
    }
}

exports.getUserByUserName = async (req, res) => {
    try {
        const { username } = req.params;
        const data = await Users.find({ username: username }).populate('username');
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
        res.status(500).json({
            "status": 500,
            "message": "Đã xảy ra lỗi khi tìm kiếm user"
        });
    }
}