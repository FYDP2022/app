package com.example.lawny_proj

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lawny_proj.databinding.FragmentSensorDataBinding
import androidx.annotation.NonNull
import com.example.lawny_proj.modules.LawnyMqttHelper


/**
 * A simple [Fragment] subclass.
 * Use the [SensorData.newInstance] factory method to
 * create an instance of this fragment.
 */

private var testval: String = ""
private lateinit var binding: FragmentSensorDataBinding

class SensorData : Fragment(R.layout.fragment_sensor_data) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSensorDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun setUltrasonic(incoming_ultrasonic: List<String>) {
        when(incoming_ultrasonic[0]) {
            "F" -> binding.ultrasonicFront.text = incoming_ultrasonic[1]
            "FL" -> binding.ultrasonicFrontLeft.text = incoming_ultrasonic[1]
            "L" -> binding.ultrasonicLeft.text = incoming_ultrasonic[1]
            "BL" -> binding.ultrasonicBackLeft.text = incoming_ultrasonic[1]
            "B" -> binding.ultrasonicBack.text = incoming_ultrasonic[1]
            "BR" -> binding.ultrasonicBackRight.text = incoming_ultrasonic[1]
            "R" -> binding.ultrasonicRight.text = incoming_ultrasonic[1]
            "FR" -> binding.ultrasonicFrontRight.text = incoming_ultrasonic[1]
            else -> {
                Log.e(tag, "Invalid Ultrasonic Sensor Identifier")
            }
        }
    }
    fun setTemperature(incoming_temperature: List<String>) {
        when(incoming_temperature[0]) {
            "Sensor1" -> binding.tempSensor1.text = incoming_temperature[1]
            "Sensor2" -> binding.tempSensor2.text = incoming_temperature[1]
            "Sensor3" -> binding.tempSensor3.text = incoming_temperature[1]
            "Sensor4" -> binding.tempSensor4.text = incoming_temperature[1]
            "Sensor5" -> binding.tempSensor5.text = incoming_temperature[1]
            else -> {
                Log.e(tag, "Invalid Temperature Sensor Identifier")
            }
        }
    }
    fun setBattery(incoming_battery: String) {
       binding.batteryData.text = incoming_battery
    }

}