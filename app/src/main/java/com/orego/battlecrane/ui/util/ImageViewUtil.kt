package com.orego.battlecrane.ui.util

import android.content.Context
import android.widget.ImageView

fun ImageView.setImageById(context: Context, id : Int) {
    this.setImageDrawable(context.getDrawable(id))
}