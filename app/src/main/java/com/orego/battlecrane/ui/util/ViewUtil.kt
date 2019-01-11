package com.orego.battlecrane.ui.util

import android.view.View
import android.view.ViewTreeObserver
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun View.onMeasured(callback: () -> Unit) {
    val getObserver: () -> ViewTreeObserver = { this.viewTreeObserver }
    getObserver().addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

        override fun onGlobalLayout() {
            callback()
            getObserver().removeOnGlobalLayoutListener(this)
        }
    })
}

suspend fun View.measure() =
    suspendCoroutine<ViewSize> { continuation ->
        val view = this
        val getObserver: () -> ViewTreeObserver = { this.viewTreeObserver }
        getObserver().addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

            override fun onGlobalLayout() {
                getObserver().removeOnGlobalLayoutListener(this)
                continuation.resume(ViewSize(view.measuredWidth, view.measuredHeight))
            }
        })
    }


data class ViewSize(val width: Int, val height: Int)

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}
