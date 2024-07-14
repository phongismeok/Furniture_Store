var express = require('express');
var router = express.Router();

const Products = require('../models/productModel')
const Carts = require('../models/cartModel')
const Types = require('../models/typeModel')
const Users = require('../models/userModel')
const Ships = require('../models/shippingModel')
const Upload = require('../config/common/upload');
const mongoose = require("mongoose");

// product
router.get('/get-list-products', async (req, res) => {
    try {
        const data = await Products.find().populate();
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
    }
})

router.get('/get-products-by-id', async (req, res) => {
    try {
        const { _id } = req.query;
        if (!_id) {
            return res.status(400).send({ error: 'ID is required' });
        }

        const data = await Products.findById(_id).populate('_id');
        if (!data) {
            return res.status(404).send({ error: 'Product not found' });
        }

        res.status(200).send(data);
    } catch (error) {
        console.log(error);
        res.status(500).send({ error: 'Internal Server Error' });
    }
});

router.get('/get-list-products-by-type/:type', async (req, res) => {
    try {
        const { type } = req.params;
        const data = await Products.find({ type: type }).populate('type');
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
        res.status(500).json({
            "status": 500,
            "message": "Đã xảy ra lỗi khi tìm kiếm sản phẩm"
        });
    }
});

router.get('/get-list-products-by-state-favorites/:stateFavorites', async (req, res) => {
    try {
        const { stateFavorites } = req.params;
        const data = await Products.find({ stateFavorites: stateFavorites }).populate('stateFavorites');
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
        res.status(500).json({
            "status": 500,
            "message": "Đã xảy ra lỗi khi tìm kiếm sản phẩm"
        });
    }
});

router.get('/get-list-products-by-typeProduct/:typeProduct', async (req, res) => {
    try {
        const { typeProduct } = req.params;
        const data = await Products.find({ typeProduct: typeProduct }).populate('typeProduct');
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
        res.status(500).json({
            "status": 500,
            "message": "Đã xảy ra lỗi khi tìm kiếm sản phẩm"
        });
    }
});

router.get('/search-product', async (req, res) => {
    try {
        const key = req.query.key ? req.query.key.toString() : '';

        const data = await Products.find({ productName: { "$regex": key, "$options": "i" } })
            .sort({ price: -1 });

        res.send(data).status(200)
    } catch (error) {
        console.log(error);
        res.send("loi").status(500)
    }
});

router.get('/search-product-favorites', async (req, res) => {
    try {
        const key = req.query.key ? req.query.key.toString() : '';

        const data = await Products.find({
            productName: { "$regex": key, "$options": "i" },
            stateFavorites: 1
        }).sort({ price: -1 });

        res.send(data).status(200);
    } catch (error) {
        console.log(error);
        res.send("loi").status(500);
    }
});

router.put('/update-state-favorites-product', async (req, res) => {
    try {
        // Lấy id từ query
        let id = req.query._id;
        console.log(id);

        // Lấy giá trị mới của thuộc tính stateFavorite từ request body
        let { stateFavorites } = req.body;

        // Cập nhật duy nhất thuộc tính stateFavorite
        await Products.updateOne({ _id: id }, { $set: { stateFavorites: stateFavorites } });

        res.status(204).send("Cập nhật thành công");

    } catch (error) {
        console.error("Lỗi khi cập nhật product:", error);
        res.status(500).send("Đã xảy ra lỗi khi cập nhật product");
    }
});

// type
router.get('/get-list-types', async (req, res) => {
    try {
        const data = await Types.find().populate();
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
    }
})

// user
router.get('/get-list-users', async (req, res) => {
    try {
        const data = await Users.find().populate();
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
    }
})

router.get('/get-user-by-username/:username', async (req, res) => {
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
});

// cart
router.get('/get-cart-by-productId/:productId', async (req, res) => {
    try {
        const { productId } = req.params;
        const data = await Carts.find({ productId: productId }).populate('productId');
        res.status(200).send(data)
        console.log(data)
    } catch (error) {
        console.log(error);
        res.status(500).json({
            "status": 500,
            "message": "Đã xảy ra lỗi khi tìm kiếm user"
        });
    }
});

router.post('/add-product-to-cart', async (req, res) => {
    try {

        let product = req.body;

        console.log(product)

        let kq = await Carts.create(product);

        if(kq){
            res.status(204).send("thêm thành công");
        }else{
            res.status(400).send("thêm thất bại");
        }

    } catch (error) {
        console.log(error);
    }

})

router.get('/get-list-cart', async (req, res) => {
    try {
        const data = await Carts.find().populate();
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
    }
})

router.put('/update-quantity-cart', async (req, res) => {
    try {
        // Lấy id từ query
        let id = req.query.productId;
        console.log(id);

        // Lấy giá trị mới của thuộc tính stateFavorite từ request body
        let { quantity } = req.body;

        // Cập nhật duy nhất thuộc tính quantity
        await Carts.updateOne({ productId: id }, { $set: { quantity: quantity } });

        res.status(204).send("Cập nhật thành công");

    } catch (error) {
        console.error("Lỗi khi cập nhật cart:", error);
        res.status(500).send("Đã xảy ra lỗi khi cập nhật cart");
    }
});

router.delete('/delete-cart/:id', async (req, res) => {

    try {
        let id = req.params.id;
        console.log(id);

        await Carts.deleteOne({ _id: id });
        res.send('xoa thanh cong')
    }catch (error){
        console.error("Lỗi khi xóa cart:", error);
        res.status(500).send("Đã xảy ra lỗi khi xóa cart");
    }

})

// shipping

router.get('/get-list-ships', async (req, res) => {
    try {
        const data = await Ships.find().populate();
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
    }
})

router.get('/get-list-ship-by-account/:account', async (req, res) => {
    try {
        const { account } = req.params;
        const data = await Ships.find({ account: account }).populate('account');
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
        res.status(500).send("loi")
    }
});

router.get('/get-list-ship-by-select/:select', async (req, res) => {
    try {
        const { select } = req.params;
        const data = await Ships.find({ select: select }).populate('select');
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
        res.status(500).send("loi")
    }
});

router.delete('/delete-ship/:id', async (req, res) => {
    try {
        let id = req.params.id;
        console.log(id);

        await Ships.deleteOne({ _id: id });
        res.send('xoa thanh cong')
    }catch (error){
        console.error("Lỗi khi xóa cart:", error);
        res.status(500).send("Đã xảy ra lỗi khi xóa cart");
    }

})

router.post('/add-shipping', async (req, res) => {
    try {

        let shipBody = req.body;

        console.log(shipBody)

        let kq = await Ships.create(shipBody);

        if(kq){
            res.status(204).send("thêm thành công");
        }else{
            res.status(400).send("thêm thất bại");
        }

    } catch (error) {
        console.log(error);
    }
})

router.put('/update-select-shipping', async (req, res) => {
    try {
        // Lấy id từ query
        let id = req.query._id;
        console.log(id);

        // Lấy giá trị mới của thuộc tính stateFavorite từ request body
        let { select } = req.body;

        // Cập nhật duy nhất thuộc tính quantity
        await Ships.updateOne({ _id: id }, { $set: { select: select } });

        res.status(204).send("Cập nhật thành công");

    } catch (error) {
        console.error("Lỗi khi cập nhật cart:", error);
        res.status(500).send("Đã xảy ra lỗi khi cập nhật cart");
    }
});

router.put('/update-shipping', async (req, res) => {
    try {
        // Lấy id từ query
        let id = req.query._id;
        console.log(id);

        // Lấy tất cả thuộc tính mới từ request body
        let updatedData = req.body;

        // Cập nhật tất cả thuộc tính của đối tượng Ships
        await Ships.updateOne({ _id: id }, { $set: updatedData });

        res.status(204).send("Cập nhật thành công");

    } catch (error) {
        console.error("Lỗi khi cập nhật Ships:", error);
        res.status(500).send("Đã xảy ra lỗi khi cập nhật Ships");
    }
});

module.exports = router;