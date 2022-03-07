package com.example.lawny_proj

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lawny_proj.databinding.FragmentLawnyMapBinding
import com.example.lawny_proj.databinding.FragmentSensorDataBinding
import com.example.lawny_proj.modules.LawnyCanvas
import java.lang.NumberFormatException

/**
 * A simple [Fragment] subclass.
 * Use the [LawnyMap.newInstance] factory method to
 * create an instance of this fragment.
 */
private lateinit var binding: FragmentLawnyMapBinding
lateinit var lcanvas: LawnyCanvas

class LawnyMap : Fragment(R.layout.fragment_lawny_map) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLawnyMapBinding.inflate(layoutInflater)
        lcanvas = LawnyCanvas(binding)
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
}