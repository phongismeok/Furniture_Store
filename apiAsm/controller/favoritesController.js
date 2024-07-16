const Favorites = require('../models/favoritesModel')

exports.getAllFavoritesByAccount = async (req, res) => {
    try {
        const { account } = req.params;
        const data = await Favorites.find({ account: account }).populate('account');
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
        res.status(500).send("loi")
    }
}

exports.getAllFavoritesByAccountAndId = async (req, res) => {
    try {
        const { account, id } = req.query;
        const data = await Favorites.find({ account: account, _id: id }).populate('account');
        res.status(200).send(data);
    } catch (error) {
        console.log(error);
        res.status(500).send("loi");
    }
}

exports.getFavoritesByAccountAndProductId = async (req, res) => {
    try {
        const { account, productId } = req.params;
        const data = await Favorites.findOne({ account: account, productId: productId }).populate('account');

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

exports.addFavorites = async (req, res) => {
    try {
        let fvBody = req.body;

        console.log(fvBody)

        let kq = await Favorites.create(fvBody);

        if(kq){
            res.status(204).send("thêm thành công");
        }else{
            res.status(400).send("thêm thất bại");
        }

    } catch (error) {
        console.log(error);
    }
}

exports.deleteFavorites = async (req, res) => {
    try {
        let id = req.params.id;
        console.log(id);

        await Favorites.deleteOne({ _id: id });
        res.send('xoa thanh cong')
    }catch (error){
        console.error("Lỗi khi xóa cart:", error);
        res.status(500).send("Đã xảy ra lỗi khi xóa cart");
    }
}

exports.searchFavorites = async (req, res) => {
    try {
        const key = req.query.key ? req.query.key.toString() : '';
        const account = req.query.account ? req.query.account.toString() : '';

        const query = {
            productName: { "$regex": key, "$options": "i" }
        };

        if (account) {
            query.account = { "$regex": account, "$options": "i" };
        }

        const data = await Favorites.find(query)
            .sort({ price: -1 });

        res.send(data).status(200);
    } catch (error) {
        console.log(error);
        res.send("loi").status(500);
    }
}