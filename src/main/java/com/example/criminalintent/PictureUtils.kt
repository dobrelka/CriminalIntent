package com.example.criminalintent

import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun getScaledBitmap(path: String, destWidth: Int, destHeight: Int): Bitmap {
    //Read in the dimensions of the image on disk
    var options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(path, options)

    val srcWidth = options.outWidth.toFloat()
    val srcHeight = options.outHeight.toFloat()

    // Figure out how mush to scale down by
    var inSampleSize = 1
    if  (srcHeight > srcHeight || srcWidth > srcWidth) {
        val heightScale = srcHeight / destHeight
        val widthScale = srcWidth / destWidth

        val sampleScale = if ( heightScale > widthScale) {
            heightScale
        } else {
            widthScale
        }
        inSampleSize = Math.round(sampleScale)
    }

    options = BitmapFactory.Options()
    options.inSampleSize = inSampleSize

    // Read in and create final bitmap
    return BitmapFactory.decodeFile(path, options)
}