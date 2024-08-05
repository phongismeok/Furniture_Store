const Comments = require('../models/commentModel')

exports.getAllCommentByProductId = async (req, res) => {
    try {
        const { productId } = req.params;
        const data = await Comments.find({ productId: productId }).populate('productId');
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
        res.status(500).send("loi")
    }
}

exports.getAllCommentByAccount = async (req, res) => {
    try {
        const { account } = req.params;
        const data = await Comments.find({ account: account }).populate('account');
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
        res.status(500).send("loi")
    }
}

exports.addComment = async (req, res) => {
    try {
        let cmtBody = req.body;

        console.log(cmtBody)

        let kq = await Comments.create(cmtBody);

        if(kq){
            res.status(204).send("thêm thành công");
        }else{
            res.status(400).send("thêm thất bại");
        }

    } catch (error) {
        console.log(error);
    }
}

exports.deleteComment = async (req, res) => {
    try {
        let id = req.params.id;
        console.log(id);

        await Comments.deleteOne({ _id: id });
        res.send('xoa thanh cong')
    }catch (error){
        console.error("Lỗi khi xóa cart:", error);
        res.status(500).send("Đã xảy ra lỗi khi xóa cart");
    }
}
