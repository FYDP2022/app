package com.example.lawny_proj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.example.lawny_proj.databinding.ActivityMainBinding
import com.example.lawny_proj.modules.LawnyMqttHelper

private lateinit var binding: ActivityMainBinding
lateinit var MqttHelper: LawnyMqttHelper
lateinit var SensorFragment: SensorData
lateinit var MapFragment: LawnyMap

class MainActivity : AppCompatActivity(), LawnyMqttHelper.SendToFragment {

    override fun sendUltrasonic(incoming_ultrasonic: List<String>) {
        SensorFragment.setUltrasonic(incoming_ultrasonic)
    }

    override fun sendTemperature(incoming_temperature: List<String>) {
        SensorFragment.setTemperature(incoming_temperature)
    }

    override fun sendBattery(incoming_battery: String) {
        SensorFragment.setBattery(incoming_battery)
    }

    override fun setImage(incoming_image: String) {
        MapFragment.setImage(incoming_image)
    }
    override fun setLawnyPosition(incoming_position: List<String>) {
        MapFragment.setPosition(incoming_position)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        SensorFragment = SensorData()
        MapFragment = LawnyMap()
        MqttHelper = LawnyMqttHelper(this)

        binding.startButton.setOnClickListener {
            MqttHelper.subscribe("PositionTopic")
            MqttHelper.subscribe("ImageTopic")
            MqttHelper.subscribe("TemperatureTopic")
            MqttHelper.subscribe("BatteryTopic")
            MqttHelper.subscribe("UltrasonicTopic")
            supportFragmentManager.beginTransaction().apply {
                add(R.id.view_fragment, SensorFragment)
                add(R.id.view_fragment, MapFragment)
                hide(MapFragment)
                commit()
            }
            binding.startButton.isVisible = false
            binding.startButton.isEnabled = false
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

    override fun onRestart() {
        super.onRestart()

    }
    fun test() {
        print("XD")
    }


}