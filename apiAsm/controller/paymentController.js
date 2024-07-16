const Payments = require('../models/paymentModel')

exports.getAllPayment = async (req, res) => {
    try {
        const data = await Payments.find();
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
    }
}

exports.getAllPaymentByAccount = async (req, res) => {
    try {
        const { account } = req.params;
        const data = await Payments.find({ account: account }).populate('account');
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
        res.status(500).send("loi")
    }
}

exports.getAllPaymentBySelectAndAccount = async (req, res) => {
    try {
        const { select, account } = req.params;
        const data = await Payments.find({ select: select, account: account }).populate('select').populate('account');
        res.status(200).send(data);
    } catch (error) {
        console.log(error);
        res.status(500).send("loi");
    }
}

exports.deletePayment = async (req, res) => {
    try {
        let id = req.params.id;
        console.log(id);

        await Payments.deleteOne({ _id: id });
        res.send('xoa thanh cong')
    }catch (error){
        console.error("Lỗi khi xóa payment:", error);
        res.status(500).send("Đã xảy ra lỗi khi xóa payment");
    }
}

exports.addPayment = async (req, res) => {
    try {

        let payBody = req.body;

        console.log(payBody)

        let kq = await Payments.create(payBody);

        if(kq){
            res.status(204).send("thêm thành công");
        }else{
            res.status(400).send("thêm thất bại");
        }

    } catch (error) {
        console.log(error);
    }
}

exports.updateSelectPayment = async (req, res) => {
    try {
        // Lấy id từ query
        let id = req.query._id;
        console.log(id);

        // Lấy giá trị mới của thuộc tính stateFavorite từ request body
        let { select } = req.body;

        // Cập nhật duy nhất thuộc tính quantity
        await Payments.updateOne({ _id: id }, { $set: { select: select } });

        res.status(204).send("Cập nhật thành công");

    } catch (error) {
        console.error("Lỗi khi cập nhật cart:", error);
        res.status(500).send("Đã xảy ra lỗi khi cập nhật cart");
    }
}

exports.updatePayment = async (req, res) => {
    try {
        // Lấy id từ query
        let id = req.query._id;
        console.log(id);

        // Lấy tất cả thuộc tính mới từ request body
        let updatedData = req.body;

        // Cập nhật tất cả thuộc tính của đối tượng Ships
        await Payments.updateOne({ _id: id }, { $set: updatedData });

        res.status(204).send("Cập nhật thành công");

    } catch (error) {
        console.error("Lỗi khi cập nhật payment:", error);
        res.status(500).send("Đã xảy ra lỗi khi cập nhật payment");
    }
}