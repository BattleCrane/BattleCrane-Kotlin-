package com.orego.battlecrane.bc.android.api.context.clickController

class BClickController {

    var currentClickMode: BClickMode? = null

    fun pushClickMode(nextClickMode: BClickMode) {
        if (this.currentClickMode == null) {
            this.currentClickMode = nextClickMode
            nextClickMode.onStart()
        } else {
            this.currentClickMode = this.currentClickMode!!.onNext(nextClickMode)
        }
    }

    fun forcePushClickMode(clickMode: BClickMode) {
        this.currentClickMode = clickMode
    }
}