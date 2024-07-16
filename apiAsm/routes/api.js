let express = require('express');
let router = express.Router();

const productController = require('../controller/productController');
const typeController = require('../controller/typeController');
const userController = require('../controller/userController');
const cartController = require('../controller/cartController');
const shipController = require('../controller/shipController');
const favoritesController = require('../controller/favoritesController');
const paymentController = require('../controller/paymentController');
const orderController = require('../controller/orderController');
const notificationController = require('../controller/notificationController');
const commentController = require('../controller/commentController');

// product
router.get('/get-list-products', productController.getAllProduct);
router.get('/get-products-by-id',productController.getProductById)
router.get('/get-list-products-by-type/:type',productController.getProductByType)
router.get('/get-list-products-by-typeProduct/:typeProduct',productController.getProductsByTypeProduct)
router.get('/search-product',productController.searchProduct)

// type
router.get('/get-list-types',typeController.getAllType)

// user
router.get('/get-list-users',userController.getAllUser)
router.get('/get-user-by-username/:username',userController.getUserByUserName)

// cart
router.get('/get-list-cart-by-account/:account',cartController.getAllUserByAccount)
router.get('/get-cart-by-productId/:account/:productId',cartController.getCartByProductIdAndAccount)
router.post('/add-product-to-cart',cartController.addProductToCart)
router.put('/update-quantity-cart',cartController.updateQuantity);
router.delete('/delete-cart/:id',cartController.deleteCart)

// shipping
router.get('/get-list-ships',shipController.getAllShip)
router.get('/get-list-ship-by-account/:account',shipController.getAllShipByAccount);
router.get('/get-list-ship-by-select/:select/:account',shipController.getAllShipBySelectAndAccount);
router.delete('/delete-ship/:id',shipController.deleteShip)
router.post('/add-shipping',shipController.addShip)
router.put('/update-select-shipping',shipController.updateSelectShip);
router.put('/update-shipping',shipController.updateShipping);

// favorites
router.get('/get-list-favorites-by-account/:account',favoritesController.getAllFavoritesByAccount);
router.get('/get-favorites-by-account-id',favoritesController.getAllFavoritesByAccountAndId);
router.get('/get-favorite-by-productId/:account/:productId',favoritesController.getFavoritesByAccountAndProductId);
router.post('/add-favorite',favoritesController.addFavorites)
router.delete('/delete-favorite/:id',favoritesController.deleteFavorites)
router.get('/search-favorites',favoritesController.searchFavorites);

// payment
router.get('/get-list-payments',paymentController.getAllPayment)
router.get('/get-list-payment-by-account/:account',paymentController.getAllPaymentByAccount);
router.get('/get-list-payment-by-select/:select/:account',paymentController.getAllPaymentBySelectAndAccount);
router.delete('/delete-payment/:id',paymentController.deletePayment)
router.post('/add-payment',paymentController.addPayment)
router.put('/update-select-payment',paymentController.updateSelectPayment);
router.put('/update-payment',paymentController.updatePayment);

module.exports = router;