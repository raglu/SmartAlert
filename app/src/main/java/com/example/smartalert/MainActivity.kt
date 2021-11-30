package com.example.smartalert

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.hivemq.client.mqtt.MqttGlobalPublishFilter
import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this);
        createNotificationChannel()
        FirebaseMessaging.getInstance().subscribeToTopic("alert")
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "test"
            val descriptionText = "Channel for alerts"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("AlertChannel", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

/*    private fun initMQTTClient() {
        val mqttClient = Mqtt5Client.builder()
            .identifier(UUID.randomUUID().toString())
            .serverHost("mqtt://broker.hivemq.com")
            .buildBlocking();

        mqttClient.connect()

        mqttClient.subscribeWith()
            .topicFilter("alert")
            .qos(MqttQos.EXACTLY_ONCE)
            .callback


    }*/
}