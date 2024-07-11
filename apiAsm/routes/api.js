var express = require('express');
var router = express.Router();

const Products = require('../models/productModel')
const Carts = require('../models/cartModel')
const Types = require('../models/typeModel')
const Users = require('../models/userModel')
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

        res.json({
            "status": 200,
            "messenger": "Thành công",
            "data": data
        });
    } catch (error) {
        console.log(error);
        res.status(500).json({
            "status": 500,
            "messenger": "Đã xảy ra lỗi khi tìm kiếm sản phẩm"
        });
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

module.exports = router;