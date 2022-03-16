package com.example.lawny_proj

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.example.lawny_proj.databinding.FragmentLawnyMapBinding
import com.example.lawny_proj.modules.LawnyCanvas

/**
 * A simple [Fragment] subclass.
 * Use the [LawnyMap.newInstance] factory method to
 * create an instance of this fragment.
 */
private lateinit var binding: FragmentLawnyMapBinding
lateinit var lcanvas: LawnyCanvas
private lateinit var activityReference: LawnyMap.WriteRemoteInterface


class LawnyMap : Fragment(R.layout.fragment_lawny_map) {
    var x = -1.0
    var y = -1.0
    var action = MotionEvent.ACTION_UP
    val remoteHandler = Handler(Looper.getMainLooper())

    fun handler_method() {
        if (action != MotionEvent.ACTION_UP) {
            when {
                (0 <= x && x < 0.333) &&  (0.666 <= y && y < 1) -> activityReference.writeRemote("BWD_LEFT")
                (0.666 <= x && x < 1) &&  (0.666 <= y && y < 1) -> activityReference.writeRemote("BWD_RIGHT")
                (0 <= x && x < 0.333) &&  (0.333 <= y && y < 0.666) -> activityReference.writeRemote("POINT_LEFT")
                (0.666 <= x && x < 1) &&  (0.333 <= y && y < 0.666) -> activityReference.writeRemote("POINT_RIGHT")
                (0 <= x && x < 0.333) &&  (0 <= y && y < 0.333) -> activityReference.writeRemote("FWD_LEFT")
                (0.666 <= x && x < 1) &&  (0 <= y && y < 0.333) -> activityReference.writeRemote("FWD_RIGHT")
                (0.333 <= x && x < 0.666) &&  (0 <= y && y < 0.666) -> activityReference.writeRemote("FORWARD")
                (0.333 <= x && x < 0.666) &&  (0.666 <= y && y < 1) -> activityReference.writeRemote("REVERSE")
            }
        } else {
            activityReference.writeRemote("STOP")
        }
    }
    interface WriteRemoteInterface {
        fun writeRemote(movement: String)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLawnyMapBinding.inflate(layoutInflater)
        lcanvas = LawnyCanvas(binding)

        activityReference = context as WriteRemoteInterface

        binding.touchpad.setOnTouchListener { p0, p1 ->
            x = (p1.x / p0.width).toDouble()
            y = (p1.y / p0.height).toDouble()
            action = p1.action
            Log.d("X POS", x.toString())
            Log.d("Y POS", y.toString())
            true
        }

        remoteHandler.post(object : Runnable {
            override fun run() {
                handler_method()
                remoteHandler.postDelayed(this, 250)
            }
        })

        return binding.root
    }

    fun setImage(incoming_image: String) {
        try {
            val data = Base64.decode(incoming_image, Base64.DEFAULT)
            val bfOptions = BitmapFactory.Options()
            bfOptions.inMutable = true
            var converted_img = BitmapFactory.decodeByteArray(data, 0, data.size, bfOptions)
            lcanvas.setImageAndCanvas(converted_img)
        } catch (e: Exception) {
            Log.e("XD", e.toString())
        }
    }

    fun setPosition(incoming_position: List<String>) {
        try {
            val pos_x = Integer.parseInt(incoming_position[0])
            val pos_y = Integer.parseInt(incoming_position[1])
            val angle = Integer.parseInt(incoming_position[2])
            lcanvas.drawPosition(pos_x, pos_y, angle)
        } catch (nfe: NumberFormatException) {
            println("Could not parse $nfe")
        }
    }

    fun setGyroscope(incoming_gyroscope: String) {
        binding.gyroscopeData.text = incoming_gyroscope.trim() + "\u00B0"
    }

    fun setAcceleration(incoming_acceleration: String) {
        binding.accelerationData.text = HtmlCompat.fromHtml(incoming_acceleration.trim() + "m/s<sup><small><small>2</small></small></sup>", HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}