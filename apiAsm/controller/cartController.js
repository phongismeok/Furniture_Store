const Carts = require('../models/cartModel')

exports.getAllUserByAccount = async (req, res) => {
    try {
        const { account } = req.params;
        const data = await Carts.find({ account: account }).populate('account');
        res.send(data).status(200)
    } catch (error) {
        console.log(error);
        res.send("loi").status(500)
    }
}

exports.getCartByProductIdAndAccount = async (req, res) => {
    try {
        const { account, productId } = req.params;
        const data = await Carts.findOne({ account: account, productId: productId }).populate('account');
        console.log(data)
        if (data) {
            res.send(data).status(200);
        } else {
            res.send("Không tìm thấy dữ liệu").status(404);
        }
    } catch (error) {
        console.log(error);
        res.status(500).send("Lỗi");
    }
}

exports.addProductToCart = async (req, res) => {
    try {
        let product = req.body;
        console.log(product)
        let kq = await Carts.create(product);
        if(kq){
            res.send("thêm thành công").status(204);
        }else{
            res.send("thêm thất bại").status(400);
        }
    } catch (error) {
        console.log(error);
    }
}

exports.updateQuantity = async (req, res) => {
    try {
        // Lấy id từ query
        let id = req.query.id;
        console.log(id);

        // Lấy giá trị mới của thuộc tính stateFavorite từ request body
        let { quantity } = req.body;
        console.log({quantity})

        // Cập nhật duy nhất thuộc tính quantity
        await Carts.updateOne({ _id: id }, { $set: { quantity: quantity } });

        res.send("Cập nhật thành công").status(204);

    } catch (error) {
        console.error("Lỗi khi cập nhật cart:", error);
        res.status(500).send("Đã xảy ra lỗi khi cập nhật cart");
    }
}

exports.deleteCart = async (req, res) => {
    try {
        let id = req.params.id;
        console.log(id);

        await Carts.deleteOne({ _id: id });
        res.send('xoa thanh cong')
    }catch (error){
        console.error("Lỗi khi xóa cart:", error);
        res.status(500).send("Đã xảy ra lỗi khi xóa cart");
    }
}

