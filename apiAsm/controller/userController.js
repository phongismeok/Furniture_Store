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
        const data = await Users.findOne({ username: username }).populate('username');

        if (data) {
            res.status(200).send(data);
        } else {
            res.status(404).send("Không tìm thấy dữ liệu");
        }
    } catch (error) {
        console.log(error);
        res.status(500).send("Lỗi");
    }
}

exports.addUser = async (req, res) => {
    try {
        let userBody = req.body;

        console.log(userBody)

        let kq = await Users.create(userBody);

        if(kq){
            res.status(204).send("thêm thành công");
        }else{
            res.status(400).send("thêm thất bại");
        }

    } catch (error) {
        console.log(error);
    }
}