package com.orego.battlecrane.ui.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout

fun ImageView.setImageById(context: Context, id: Int) {
    this.setImageDrawable(context.getDrawable(id))
}

fun ImageView.setImageByAssets(context: Context, path: String) {
    val stream = context.assets.open(path)
    val drawable = Drawable.createFromStream(stream, null)
    this.setImageDrawable(drawable)
}

//TODO: REMOVE CONTEXT PARAM!!!
fun ImageView.byResource(context: Context, width: Int, heigth: Int, resourceId: Int): ImageView {
    this.id = View.generateViewId()
    this.setImageById(context, resourceId)
    this.layoutParams = ConstraintLayout.LayoutParams(width, heigth)
    return this
}

fun ImageView.byAssets(context: Context, wigth: Int, heigth: Int, path: String): ImageView {
    this.id = View.generateViewId()
    this.setImageByAssets(context, path)
    this.layoutParams = ConstraintLayout.LayoutParams(wigth, heigth)
    return this
}