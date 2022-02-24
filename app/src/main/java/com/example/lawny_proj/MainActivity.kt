package com.example.lawny_proj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.lawny_proj.databinding.ActivityMainBinding
import com.example.lawny_proj.modules.LawnyCanvas
import com.example.lawny_proj.modules.LawnyMqttHelper

private lateinit var binding: ActivityMainBinding
lateinit var MqttHelper: LawnyMqttHelper
lateinit var lawnyCanvas: LawnyCanvas

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)

        lawnyCanvas = LawnyCanvas(this, binding)
        MqttHelper = LawnyMqttHelper(this, binding, lawnyCanvas)

        binding.SubscribeTest.setOnClickListener {
            MqttHelper.subscribe("TestTopic")
            MqttHelper.subscribe("ImageTopic")
            MqttHelper.subscribe("TemperatureTopic")
            MqttHelper.subscribe("BatteryTopic")
        }

        binding.temp.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {

            }
        })

        binding.battery.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(editable: Editable?) {

            }
        })
    }

    fun tempChangeCallback() {

    }

    fun batteryChangeCallback() {

    }

    override fun onRestart() {
        super.onRestart()

    }



}