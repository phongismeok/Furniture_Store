const Notifications = require('../models/notificationModel')

exports.getAllNotificationByAccount = async (req, res) => {
    try {
        const { account } = req.params;
        const data = await Notifications.find({ account: account }).populate('account');
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
        res.status(500).send("loi")
    }
}

exports.addNotification = async (req, res) => {
    try {
        let notificationBody = req.body;

        console.log(notificationBody)

        let kq = await Notifications.create(notificationBody);

        if(kq){
            res.status(204).send("thêm thành công");
        }else{
            res.status(400).send("thêm thất bại");
        }

    } catch (error) {
        console.log(error);
    }
}

exports.updateState = async (req, res) => {
    try {
        // Lấy id từ query
        let id = req.query.id;
        console.log(id);

        // Lấy giá trị mới của thuộc tính stateFavorite từ request body
        let { state } = req.body;
        console.log({state})

        // Cập nhật duy nhất thuộc tính quantity
        await Notifications.updateOne({ _id: id }, { $set: { state: state } });

        res.send("Cập nhật thành công").status(204);

    } catch (error) {
        console.error("Lỗi khi cập nhật notification:", error);
        res.status(500).send("Đã xảy ra lỗi khi cập nhật notification");
    }
}

exports.deleteNotification = async (req, res) => {
    try {
        let id = req.params.id;
        console.log(id);

        await Notifications.deleteOne({ _id: id });
        res.send('xoa thanh cong')
    }catch (error){
        console.error("Lỗi khi xóa cart:", error);
        res.status(500).send("Đã xảy ra lỗi khi xóa cart");
    }
}

exports.searchNotification = async (req, res) => {
    try {
        const key = req.query.key ? req.query.key.toString() : '';
        const account = req.query.account ? req.query.account.toString() : '';

        const query = {
            content: { "$regex": key, "$options": "i" }
        };

        if (account) {
            query.account = { "$regex": account, "$options": "i" };
        }

        const data = await Notifications.find(query)

        res.send(data).status(200);
    } catch (error) {
        console.log(error);
        res.send("loi").status(500);
    }
}