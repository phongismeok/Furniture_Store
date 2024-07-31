const admin = require('firebase-admin');
const serviceAccount = require("../config/furniture-store-27118-firebase-adminsdk-gwedn-de88129f24.json");

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
});

exports.sendNotification = async (req, res) => {
    const { token, title, body } = req.body;

    const message = {
        notification: {
            title: title,
            body: body,
        },
        token: token,
    };

    admin.messaging().send(message)
        .then((response) => {
            res.status(200).send('Successfully sent message: ' + response);
        })
        .catch((error) => {
            res.status(500).send('Error sending message: ' + error);
        });
}


