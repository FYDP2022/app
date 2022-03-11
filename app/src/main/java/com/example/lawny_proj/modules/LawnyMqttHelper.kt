package com.example.lawny_proj.modules

import android.content.Context
import android.util.Log
import info.mqtt.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.MqttConnectOptions

class LawnyMqttHelper(context: Context) {

    interface SendToFragment {
        fun sendUltrasonic(incoming_ultrasonic: List<String>)
        fun sendTemperature(incoming_temperature: List<String>)
        fun sendBattery(incoming_battery: String)
        fun setImage(incoming_image: String)
        fun setLawnyPosition(incoming_position: List<String>)
        fun sendGyroscope(incoming_gyroscope: String)
        fun sendAcceleration(incoming_acceleration: String)
    }
    var mqttAndroidClient: MqttAndroidClient
    val serverUri = "tcp://10.0.0.170:1883"
    val clientId = "LawnyClient"
    val tag = "Lawny_Mqtt_Client"

    init {
        var activityReference = context as SendToFragment

        mqttAndroidClient = MqttAndroidClient(context, serverUri, clientId)
        mqttAndroidClient.setCallback(object: MqttCallbackExtended {
            override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                Log.v(tag, "LawnyMqttClient Connection Complete")
            }

            override fun connectionLost(cause: Throwable?) {
                Log.v(tag, "Connection Lost: ${cause.toString()}")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                Log.v(tag, "Message Sent")
            }

            override fun messageArrived(topic: String, message: MqttMessage) {
                Log.v(tag, " Message Received from Topic: $topic, message: ${message.toString()}")
                when(topic) {
                    "TemperatureTopic" -> activityReference.sendTemperature(message.toString().split(":"))
                    "UltrasonicTopic" -> activityReference.sendUltrasonic(message.toString().split(":"))
                    "BatteryTopic" -> activityReference.sendBattery(message.toString())
                    "ImageTopic" -> activityReference.setImage(message.toString())
                    "PositionTopic" -> activityReference.setLawnyPosition(message.toString().split(":"))
                    "GyroscopeTopic" -> activityReference.sendGyroscope(message.toString())
                    "AccelerationTopic" -> activityReference.sendAcceleration(message.toString())
                }
            }
        })

        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.isCleanSession = true
        mqttConnectOptions.keepAliveInterval = 30
        mqttConnectOptions.userName = "lawny_app"

        mqttAndroidClient.connect(mqttConnectOptions, null, object : IMqttActionListener {
            override fun onSuccess(asyncActionToken: IMqttToken?) {
                Log.d(tag, "Connection_Status_Success")
            }

            override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                Log.d(tag, "Connection_Status_Failure: ${exception.toString()}")
            }
        })
    }

    fun subscribe(topic: String) {
        try {
            mqttAndroidClient.subscribe(topic, 2, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(tag,"Subscribed to $topic successfully")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(tag,"Failed to subscribe $topic for reason ${exception.toString()}")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun publish(topic: String, msg: String, qos: Int = 2, retained: Boolean = false) {
        try {
            mqttAndroidClient.publish(topic, msg.toByteArray(), qos, retained, null, object : IMqttActionListener{
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.d(tag,"Published to $topic successfully")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.d(tag,"Failed to publish to $topic for reason ${exception.toString()}")
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
}
