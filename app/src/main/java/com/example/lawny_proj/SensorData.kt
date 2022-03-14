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
import androidx.core.text.HtmlCompat
import com.example.lawny_proj.modules.LawnyMqttHelper


/**
 * A simple [Fragment] subclass.
 * Use the [SensorData.newInstance] factory method to
 * create an instance of this fragment.
 */

private lateinit var binding: FragmentSensorDataBinding
private lateinit var activityReference: SensorData.SetWarningInterface

class SensorData : Fragment(R.layout.fragment_sensor_data) {

    interface SetWarningInterface {
        fun setTempWarning(id: String, showWarning: Boolean)
        fun setUltrasonicWarning(id: String, showWarning: Boolean)
        fun setBatteryWarning(id: String, showWarning: Boolean)
    }

    private class TempCheckWatcher(private val text: TextView) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            var text_val = Integer.parseInt(p0.toString())
            if (text_val >= 80) {
                text.setTextColor(Color.RED)
                activityReference.setTempWarning(text.id.toString(), true)
            } else {
                text.setTextColor(Color.BLACK)
                activityReference.setTempWarning(text.id.toString(), false)
            }
            text.text = p0.toString() + "\u2103"
        }
    }

    private class UltrasonicCheckWatcher(private val text: TextView) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            var text_val = Integer.parseInt(p0.toString())
            if (text_val <= 15) {
                text.setTextColor(Color.RED)
                activityReference.setUltrasonicWarning(text.id.toString(), true)
            } else {
                text.setTextColor(Color.BLACK)
                activityReference.setUltrasonicWarning(text.id.toString(), false)

            }
            text.text = HtmlCompat.fromHtml(p0.toString() + "cm<small>s</small>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activityReference = context as SetWarningInterface
        binding = FragmentSensorDataBinding.inflate(inflater, container, false)

        binding.BATTERY.addTextChangedListener(TempCheckWatcher(binding.BATTERY))
        binding.LEFTDRIVE.addTextChangedListener(TempCheckWatcher(binding.LEFTDRIVE))
        binding.RIGHTDRIVE.addTextChangedListener(TempCheckWatcher(binding.RIGHTDRIVE))
        binding.CUTTINGMOTOR.addTextChangedListener(TempCheckWatcher(binding.CUTTINGMOTOR))
        binding.AMBIENT.addTextChangedListener(TempCheckWatcher(binding.AMBIENT))

        binding.ultrasonicFront.addTextChangedListener(UltrasonicCheckWatcher(binding.ultrasonicFront))
        binding.ultrasonicBackLeft.addTextChangedListener(UltrasonicCheckWatcher(binding.ultrasonicBackLeft))
        binding.ultrasonicBackRight.addTextChangedListener(UltrasonicCheckWatcher(binding.ultrasonicBackRight))
        binding.ultrasonicFrontLeft.addTextChangedListener(UltrasonicCheckWatcher(binding.ultrasonicFrontLeft))
        binding.ultrasonicFrontRight.addTextChangedListener(UltrasonicCheckWatcher(binding.ultrasonicFrontRight))
        binding.ultrasonicLeft.addTextChangedListener(UltrasonicCheckWatcher(binding.ultrasonicLeft))
        binding.ultrasonicRight.addTextChangedListener(UltrasonicCheckWatcher(binding.ultrasonicRight))

        return binding.root
    }

    fun setUltrasonic(incoming_ultrasonic: List<String>) {
        when(incoming_ultrasonic[0]) {
            "F" -> binding.ultrasonicFront.text = incoming_ultrasonic[1].trim()
            "FL" -> binding.ultrasonicFrontLeft.text = incoming_ultrasonic[1].trim()
            "L" -> binding.ultrasonicLeft.text = incoming_ultrasonic[1].trim()
            "BL" -> binding.ultrasonicBackLeft.text = incoming_ultrasonic[1].trim()
            "BR" -> binding.ultrasonicBackRight.text = incoming_ultrasonic[1].trim()
            "R" -> binding.ultrasonicRight.text = incoming_ultrasonic[1].trim()
            "FR" -> binding.ultrasonicFrontRight.text = incoming_ultrasonic[1].trim()
            else -> {
                Log.e(tag, "Invalid Ultrasonic Sensor Identifier")
            }
        }
    }
    fun setTemperature(incoming_temperature: List<String>) {
        when(incoming_temperature[0]) {
            "BATTERY" -> binding.BATTERY.text = incoming_temperature[1].trim()
            "LEFT_DRIVE" -> binding.LEFTDRIVE.text = incoming_temperature[1].trim()
            "RIGHT_DRIVE" -> binding.RIGHTDRIVE.text = incoming_temperature[1].trim()
            "CUTTING_MOTOR" -> binding.CUTTINGMOTOR.text = incoming_temperature[1].trim()
            "AMBIENT" -> binding.AMBIENT.text = incoming_temperature[1].trim()
            else -> {
                Log.e(tag, "Invalid Temperature Sensor Identifier")
            }
        }
    }

}