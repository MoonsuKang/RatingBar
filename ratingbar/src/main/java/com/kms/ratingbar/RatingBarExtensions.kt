package com.kms.ratingbar

import android.content.Context

fun Context.dpToPx(dp: Int): Float {
    return this.resources.displayMetrics.density * dp
}
