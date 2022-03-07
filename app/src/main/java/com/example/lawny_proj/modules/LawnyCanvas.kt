package com.example.lawny_proj.modules

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.View
import com.example.lawny_proj.databinding.FragmentLawnyMapBinding


class LawnyCanvas(private val rootBinding: FragmentLawnyMapBinding) {
    lateinit var drawCanvas: Canvas
    lateinit var img: Bitmap
    lateinit var plain_img: Bitmap

    fun setImageAndCanvas(mapImage: Bitmap) {
        var imgSize = Rect(0,0,mapImage.width, mapImage.height)
        try {
            plain_img = mapImage.copy(mapImage.config, true)
            img = mapImage.copy(mapImage.config, true)
            drawCanvas = Canvas(img)
            rootBinding.map.setImageBitmap(img)
        }
        catch (e: Exception) {
            Log.d("drawEx", e.toString())
        }
    }

    fun drawPosition(x: Int, y: Int, angle: Int) {
        var paint = Paint()
        paint.isAntiAlias = true
        paint.isDither = true
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.MITER
        paint.strokeCap = Paint.Cap.SQUARE
        paint.color = Color.RED
        paint.strokeWidth = 16F
        paint.alpha = 100
        img = plain_img.copy(plain_img.config, true)
        drawCanvas = Canvas(img)
        drawCanvas.save()
        drawCanvas.rotate(angle.toFloat(), x.toFloat(), y.toFloat())
        drawTriangle(x,y)
        drawCanvas.restore()
        rootBinding.map.setImageBitmap(img)
    }

    private fun drawTriangle(x: Int, y: Int) {
        var paint = Paint()
        paint.strokeWidth = 3f
        paint.color = Color.RED
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.isAntiAlias = true

        val bottom_left_point = Point(x-10, y-10)
        val bottom_right_point = Point(x+10, y-10)
        val top_point = Point(x, y+10)

        val path = Path()
        path.fillType = Path.FillType.EVEN_ODD
        path.moveTo(bottom_left_point.x.toFloat(), bottom_left_point.y.toFloat())
        path.lineTo(bottom_right_point.x.toFloat(), bottom_right_point.y.toFloat())
        path.lineTo(top_point.x.toFloat(), top_point.y.toFloat())
        path.close()

        drawCanvas.drawPath(path, paint);
    }
}