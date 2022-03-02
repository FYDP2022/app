package com.example.lawny_proj

import android.hardware.Sensor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isVisible
import com.example.lawny_proj.databinding.ActivityMainBinding
import com.example.lawny_proj.modules.LawnyCanvas
import com.example.lawny_proj.modules.LawnyMqttHelper

private lateinit var binding: ActivityMainBinding
lateinit var MqttHelper: LawnyMqttHelper
lateinit var lawnyCanvas: LawnyCanvas
lateinit var SensorFragment: SensorData
lateinit var MapFragment: LawnyMap

class MainActivity : AppCompatActivity(), LawnyMqttHelper.SendToFragment {

    override fun sendUltrasonic(incoming_ultrasonic: List<String>) {
        if (SensorFragment.isVisible) {
            SensorFragment.setUltrasonic(incoming_ultrasonic)
        }
    }

    override fun sendTemperature(incoming_temperature: List<String>) {
        if (SensorFragment.isVisible) {
            SensorFragment.setTemperature(incoming_temperature)
        }
    }

    override fun sendBattery(incoming_battery: String) {
        if (SensorFragment.isVisible) {
            SensorFragment.setBattery(incoming_battery)
        }
    }

    override fun setImage(incoming_image: String) {
        if (MapFragment.isVisible) {
            MapFragment.setImage(incoming_image)
        }
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
            MqttHelper.subscribe("TestTopic")
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