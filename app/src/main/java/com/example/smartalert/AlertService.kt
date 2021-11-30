package com.example.smartalert

import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class AlertService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")

        if(remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
        }

        when {
            remoteMessage.data.isEmpty() -> Log.d(TAG, "Message data payload: " + remoteMessage.data)
            remoteMessage.notification != null -> {
                remoteMessage.notification?.let {
                    Log.d(TAG, "Message Notification Title: ${it.title}")
                    Log.d(TAG, "Message Notification Body: ${it.body}")

                    sendNotification(it.title!!, it.body!!)
                }
            }
        }



        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }


    private fun sendNotification(title: String, msgBody: String) {

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, "AlertChannel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(msgBody)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(NotificationID.id, builder.build())
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }
}