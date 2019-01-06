package com.orego.battlecrane.ui.util

import android.view.View
import android.view.ViewTreeObserver
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun View.onMeasured(callback: () -> Unit) {
    val observer = this.viewTreeObserver
    observer.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

        override fun onGlobalLayout() {
            callback()
            observer.removeOnGlobalLayoutListener(this)
        }
    })
}

suspend fun View.measure() =
    suspendCoroutine<ViewSize> { continuation ->
        val view = this
        val observer = this.viewTreeObserver
        observer.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

            override fun onGlobalLayout() {
                observer.removeOnGlobalLayoutListener(this)
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
