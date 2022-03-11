package com.example.lawny_proj

import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.example.lawny_proj.databinding.ActivityMainBinding
import com.example.lawny_proj.modules.LawnyMqttHelper
import kotlin.system.exitProcess

private lateinit var binding: ActivityMainBinding
lateinit var MqttHelper: LawnyMqttHelper
lateinit var SensorFragment: SensorData
lateinit var MapFragment: LawnyMap

class MainActivity : AppCompatActivity(), LawnyMqttHelper.SendToFragment, SensorData.SetWarningInterface {

    var warn_temperature = HashMap<String, Boolean>()
    var warn_ultrasonic = HashMap<String, Boolean>()
    var warn_battery = HashMap<String, Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        SensorFragment = SensorData()
        MapFragment = LawnyMap()
        MqttHelper = LawnyMqttHelper(this)

        binding.errorReading.text = "WARNING - Sensor in Bad State"
        binding.errorReading.setBackgroundColor(Color.RED)
        binding.errorReading.visibility = View.INVISIBLE

        binding.startButton.setOnClickListener {
            MqttHelper.subscribe("PositionTopic")
            MqttHelper.subscribe("ImageTopic")
            MqttHelper.subscribe("TemperatureTopic")
            MqttHelper.subscribe("BatteryTopic")
            MqttHelper.subscribe("UltrasonicTopic")
            MqttHelper.subscribe("GyroscopeTopic")
            MqttHelper.subscribe("AccelerationTopic")
            supportFragmentManager.beginTransaction().apply {
                add(R.id.view_fragment, SensorFragment)
                add(R.id.view_fragment, MapFragment)
                hide(MapFragment)
                commit()
            }
            MqttHelper.publish("StartStopTopic", "START")

            binding.startButton.text = "STOP"
            binding.startButton.setOnClickListener {
                MqttHelper.publish("StartStopTopic", "STOP")
                this.finish()
            }
        }
        binding.dataFragmentBtn.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                hide(MapFragment)
                show(SensorFragment)
                commit()
            }
        }

        binding.lawnyViewFragmentBtn.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                hide(SensorFragment)
                show(MapFragment)
                commit()
            }
        }

    }

    override fun sendUltrasonic(incoming_ultrasonic: List<String>) {
        SensorFragment.setUltrasonic(incoming_ultrasonic)
    }
    override fun sendTemperature(incoming_temperature: List<String>) {
        SensorFragment.setTemperature(incoming_temperature)
    }
    override fun sendBattery(incoming_battery: String) {
        SensorFragment.setBattery(incoming_battery)
    }
    override fun sendGyroscope(incoming_gyroscope: String) {
        SensorFragment.setGyroscope(incoming_gyroscope)
    }
    override fun sendAcceleration(incoming_acceleration: String) {
        SensorFragment.setAcceleration(incoming_acceleration)
    }
    override fun setImage(incoming_image: String) {
        MapFragment.setImage(incoming_image)
    }
    override fun setLawnyPosition(incoming_position: List<String>) {
        MapFragment.setPosition(incoming_position)
    }


    override fun setTempWarning(id: String, showWarning: Boolean) {
        warn_temperature[id] = showWarning
        setWarning()
    }
    override fun setUltrasonicWarning(id: String, showWarning: Boolean) {
        warn_ultrasonic[id] = showWarning
        setWarning()
    }
    override fun setBatteryWarning(id: String, showWarning: Boolean) {
        warn_battery[id] = showWarning
        setWarning()
    }

    fun setWarning() {
        if (warn_battery.containsValue(true) or warn_temperature.containsValue(true) or warn_ultrasonic.containsValue(true)) {
            binding.errorReading.visibility = View.VISIBLE
        } else {
            binding.errorReading.visibility = View.INVISIBLE
        }
    }

}