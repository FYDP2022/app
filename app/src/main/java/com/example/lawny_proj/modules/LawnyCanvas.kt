package com.example.lawny_proj.modules

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.View
import com.example.lawny_proj.databinding.ActivityMainBinding


class LawnyCanvas(context: Context, val rootBinding: ActivityMainBinding): View(context) {

    fun drawMap(updateImg: Bitmap) {
        rootBinding.TestImage.setImageBitmap(updateImg)
        var imgSize = Rect(0,0,updateImg.width, updateImg.height)
        try {
            var currentDraw = Canvas(updateImg)
            currentDraw.drawBitmap(updateImg, null, imgSize, null)
            var paint = Paint()
            paint.isAntiAlias = true
            paint.isDither = true
            paint.style = Paint.Style.STROKE
            paint.strokeJoin = Paint.Join.MITER
            paint.strokeCap = Paint.Cap.SQUARE
            paint.color = Color.RED
            paint.strokeWidth = 16F
            paint.alpha = 100
            currentDraw.drawLine(0F, 0F, 1000F, 1000F, paint)

            //rootBinding.TestImage.setImageBitmap(updateImg)
            //rootBinding.TestImage.setImageBitmap(currentDraw)
        }
        catch (e: Exception) {
            Log.d("drawEx", e.toString())
        }

    }
}