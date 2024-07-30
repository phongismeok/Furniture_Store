const Orders = require('../models/orderModel')

exports.getAllOrderByAccount = async (req, res) => {
    try {
        const { account } = req.params;
        const data = await Orders.find({ account: account }).populate('account');
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
        res.status(500).send("loi")
    }
}

exports.getOrderByState = async (req, res) => {
    try {
        const { account, state } = req.params;
        const data = await Orders.find({ account: account, state: state }).populate('account');
        res.status(200).send(data);
    } catch (error) {
        console.log(error);
        res.status(500).send("Lỗi");
    }
}

exports.addOrder = async (req, res) => {
    try {
        let orderBody = req.body;

        console.log(orderBody)

        let kq = await Orders.create(orderBody);

        if(kq){
            res.status(204).send("thêm thành công");
        }else{
            res.status(400).send("thêm thất bại");
        }

    } catch (error) {
        console.log(error);
    }
}

exports.updateStateOrder = async (req, res) => {
    try {
        // Lấy id từ query
        let id = req.query.id;
        console.log(id);

        // Lấy giá trị mới của thuộc tính stateFavorite từ request body
        let { state } = req.body;
        console.log({state})

        // Cập nhật duy nhất thuộc tính quantity
        await Orders.updateOne({ _id: id }, { $set: { state: state } });

        res.send("Cập nhật thành công").status(204);

    } catch (error) {
        console.error("Lỗi khi cập nhật order:", error);
        res.status(500).send("Đã xảy ra lỗi khi cập nhật order");
    }
}

exports.deleteOrder = async (req, res) => {
    try {
        let id = req.params.id;
        console.log(id);

        await Orders.deleteOne({ _id: id });
        res.send('xoa thanh cong')
    }catch (error){
        console.error("Lỗi khi xóa order:", error);
        res.status(500).send("Đã xảy ra lỗi khi xóa order");
    }
}