const Products = require('../models/productModel')

exports.getAllProduct = async (req, res) => {
    try {
        const data = await Products.find();
        res.status(200).send(data)
    } catch (error) {
        console.log(error);
    }
}

exports.getProductById = async (req, res) => {
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
}

exports.getProductByType = async (req, res) => {
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
}

exports.getProductsByTypeProduct = async (req, res) => {
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
}

exports.searchProduct = async (req, res) => {
    try {
        const key = req.query.key ? req.query.key.toString() : '';

        const data = await Products.find({ productName: { "$regex": key, "$options": "i" } })
            .sort({ price: -1 });

        res.send(data).status(200)
    } catch (error) {
        console.log(error);
        res.send("loi").status(500)
    }
}


