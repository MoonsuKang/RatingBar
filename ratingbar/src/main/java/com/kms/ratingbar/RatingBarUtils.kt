package com.kms.ratingbar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat

/**
 * Converts a vector drawable resource into a Bitmap.
 *
 * @param context The context to access the drawable resource.
 * @param drawableId The resource ID of the vector drawable.
 * @return A Bitmap created from the specified vector drawable.
 * @throws IllegalArgumentException if the drawable is not found.
 */

fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
    val drawable = ContextCompat.getDrawable(context, drawableId)
        ?: throw IllegalArgumentException("Drawable not found")

    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}
