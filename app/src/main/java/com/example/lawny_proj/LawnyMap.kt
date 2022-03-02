package com.example.lawny_proj

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lawny_proj.databinding.FragmentLawnyMapBinding


/**
 * A simple [Fragment] subclass.
 * Use the [LawnyMap.newInstance] factory method to
 * create an instance of this fragment.
 */
private lateinit var binding: FragmentLawnyMapBinding

class LawnyMap : Fragment(R.layout.fragment_lawny_map) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLawnyMapBinding.inflate(layoutInflater)
    }

    fun setImage(incoming_image: String) {
        val data = Base64.decode(incoming_image, Base64.DEFAULT)
        val bfOptions = BitmapFactory.Options()
        bfOptions.inMutable = true
        var converted_img = BitmapFactory.decodeByteArray(data, 0, data.size, bfOptions)
        //canvas.drawMap(converted_img)
    }
}