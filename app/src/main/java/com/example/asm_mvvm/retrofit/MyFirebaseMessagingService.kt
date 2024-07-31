package com.example.asm_mvvm.retrofit

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.asm_mvvm.MainActivity
import com.example.asm_mvvm.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val CHANNEL_ID = "my_channel_id"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        remoteMessage.notification?.let {
            // hiển thị thông báo
            sendNotification(it.title, it.body)
        }
    }

    // hiển thị thông báo không chồng lên nhau
//    private fun sendNotification(title: String?, messageBody: String?) {
//        val intent = Intent(this, MainActivity::class.java).apply {
//            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        }
//        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
//            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
//
//        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setSmallIcon(R.drawable.iceye) // Thay đổi icon nếu cần
//            .setContentTitle(title)
//            .setContentText(messageBody)
//            .setAutoCancel(true)
//            .setContentIntent(pendingIntent)
//
//        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        // Tạo kênh thông báo cho Android O và các phiên bản cao hơn
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                CHANNEL_ID,
//                "My Channel Name",
//                NotificationManager.IMPORTANCE_DEFAULT
//            ).apply {
//                description = "My Channel Description"
//            }
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        notificationManager.notify(0, notificationBuilder.build())
//    }

    // hiển thị thông báo chồng lên nhau
    private fun sendNotification(title: String?, messageBody: String?) {
        val notificationId = generateUniqueNotificationId()
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.icbag) // Thay đổi icon nếu cần
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Tạo kênh thông báo cho Android O và các phiên bản cao hơn
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "My Channel Name",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "My Channel Description"
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    private fun generateUniqueNotificationId(): Int {
        return (System.currentTimeMillis() % 100000).toInt()
    }

}


