var nodemailer = require("nodemailer");
const transporter = nodemailer.createTransport({
    service: "gmail",
    auth: {
        user: "phongclanrok1@gmail.com",
        pass: "ufaa sinv puqq umso"
    }
});
module.exports = transporter 