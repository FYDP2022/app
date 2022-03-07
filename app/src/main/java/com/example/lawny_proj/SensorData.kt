package com.example.lawny_proj

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lawny_proj.databinding.FragmentSensorDataBinding
import android.widget.TextView


/**
 * A simple [Fragment] subclass.
 * Use the [SensorData.newInstance] factory method to
 * create an instance of this fragment.
 */

private var testval: String = ""
private lateinit var binding: FragmentSensorDataBinding

class SensorData : Fragment(R.layout.fragment_sensor_data) {

    private class TempCheckWatcher(private val text: TextView) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            var text_val = Integer.parseInt(p0.toString())
            if (text_val >= 80) {
                text.setTextColor(Color.RED)
            } else {
                text.setTextColor(Color.BLACK)
            }
        }
    }

    private class UltrasonicCheckWatcher(private val text: TextView) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            var text_val = Integer.parseInt(p0.toString())
            if (text_val < 10 || text_val > 50) {
                text.setTextColor(Color.RED)
            } else {
                text.setTextColor(Color.BLACK)
            }
            text.text = p0.toString() + " CMs"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSensorDataBinding.inflate(inflater, container, false)

        binding.tempSensor1.addTextChangedListener(TempCheckWatcher(binding.tempSensor1))
        binding.tempSensor2.addTextChangedListener(TempCheckWatcher(binding.tempSensor2))
        binding.tempSensor3.addTextChangedListener(TempCheckWatcher(binding.tempSensor3))
        binding.tempSensor4.addTextChangedListener(TempCheckWatcher(binding.tempSensor4))
        binding.tempSensor5.addTextChangedListener(TempCheckWatcher(binding.tempSensor5))

        binding.ultrasonicFront.addTextChangedListener(UltrasonicCheckWatcher(binding.ultrasonicFront))
        binding.ultrasonicBack.addTextChangedListener(UltrasonicCheckWatcher(binding.ultrasonicBack))
        binding.ultrasonicBackLeft.addTextChangedListener(UltrasonicCheckWatcher(binding.ultrasonicBackLeft))
        binding.ultrasonicBackRight.addTextChangedListener(UltrasonicCheckWatcher(binding.ultrasonicBackRight))
        binding.ultrasonicFrontLeft.addTextChangedListener(UltrasonicCheckWatcher(binding.ultrasonicFrontLeft))
        binding.ultrasonicFrontRight.addTextChangedListener(UltrasonicCheckWatcher(binding.ultrasonicFrontRight))
        binding.ultrasonicLeft.addTextChangedListener(UltrasonicCheckWatcher(binding.ultrasonicLeft))
        binding.ultrasonicRight.addTextChangedListener(UltrasonicCheckWatcher(binding.ultrasonicRight))

        binding.batteryData.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                var text_val = Integer.parseInt(p0.toString())
                if (text_val > 100 || text_val <= 10) {
                    binding.batteryData.setTextColor(Color.RED)
                } else if (text_val > 10 && text_val <= 25) {
                    binding.batteryData.setTextColor(Color.YELLOW)
                } else {
                    binding.batteryData.setTextColor(Color.GREEN)
                }
                binding.batteryData.text = p0.toString() + "%"
            }
        })
        binding.gyroscopeData.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                var text_val = Integer.parseInt(p0.toString())
                if (text_val < 10 || text_val > -10) {
                    binding.batteryData.setTextColor(Color.RED)
                } else {
                    binding.batteryData.setTextColor(Color.BLACK)
                }
                binding.batteryData.text = p0.toString() + " Degrees"
            }
        })

        binding.speedData.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                var text_val = Integer.parseInt(p0.toString())
                if (text_val > 100 || text_val <= 10) {
                    binding.batteryData.setTextColor(Color.RED)
                } else if (text_val > 0.4 && text_val < 0.05) {
                    binding.batteryData.setTextColor(Color.BLACK)
                }
                binding.batteryData.text = p0.toString() + " M/s"
            }
        })

        return binding.root
    }

    fun setUltrasonic(incoming_ultrasonic: List<String>) {
        when(incoming_ultrasonic[0]) {
            "F" -> binding.ultrasonicFront.text = incoming_ultrasonic[1] + " cm"
            "FL" -> binding.ultrasonicFrontLeft.text = incoming_ultrasonic[1]+ " cm"
            "L" -> binding.ultrasonicLeft.text = incoming_ultrasonic[1]+ " cm"
            "BL" -> binding.ultrasonicBackLeft.text = incoming_ultrasonic[1]+ " cm"
            "B" -> binding.ultrasonicBack.text = incoming_ultrasonic[1]+ " cm"
            "BR" -> binding.ultrasonicBackRight.text = incoming_ultrasonic[1]+ " cm"
            "R" -> binding.ultrasonicRight.text = incoming_ultrasonic[1]+ " cm"
            "FR" -> binding.ultrasonicFrontRight.text = incoming_ultrasonic[1]+ " cm"
            else -> {
                Log.e(tag, "Invalid Ultrasonic Sensor Identifier")
            }
        }
    }
    fun setTemperature(incoming_temperature: List<String>) {
        when(incoming_temperature[0]) {
            "Sensor1" -> binding.tempSensor1.text = incoming_temperature[1] + " C"
            "Sensor2" -> binding.tempSensor2.text = incoming_temperature[1] + " C"
            "Sensor3" -> binding.tempSensor3.text = incoming_temperature[1] + " C"
            "Sensor4" -> binding.tempSensor4.text = incoming_temperature[1] + " C"
            "Sensor5" -> binding.tempSensor5.text = incoming_temperature[1] + " C"
            else -> {
                Log.e(tag, "Invalid Temperature Sensor Identifier")
            }
        }
    }
    fun setBattery(incoming_battery: String) {
       binding.batteryData.text = "$incoming_battery%"
    }

}