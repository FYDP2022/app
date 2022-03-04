package com.example.lawny_proj.modules

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.View
import com.example.lawny_proj.databinding.FragmentLawnyMapBinding


class LawnyCanvas(private val rootBinding: FragmentLawnyMapBinding) {
    lateinit var drawCanvas: Canvas
    lateinit var img: Bitmap

    fun setImageAndCanvas(mapImage: Bitmap) {
        var imgSize = Rect(0,0,mapImage.width, mapImage.height)
        try {
            img = mapImage.copy(mapImage.config, true)
            drawCanvas = Canvas(img)
            drawCanvas.drawBitmap(img, null, imgSize, null)
            rootBinding.map.setImageBitmap(img)
        }
        catch (e: Exception) {
            Log.d("drawEx", e.toString())
        }
    }

    fun drawPosition(x: Int, y: Int) {
        var paint = Paint()
        paint.isAntiAlias = true
        paint.isDither = true
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.MITER
        paint.strokeCap = Paint.Cap.SQUARE
        paint.color = Color.RED
        paint.strokeWidth = 16F
        paint.alpha = 100
        drawCanvas = Canvas(img)
        drawCanvas.drawCircle(200f, 200f, 20f, paint)
        rootBinding.map.setImageBitmap(img)
    }
}