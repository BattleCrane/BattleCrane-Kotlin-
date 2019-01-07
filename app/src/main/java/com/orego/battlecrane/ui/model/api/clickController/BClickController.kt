package com.orego.battlecrane.ui.model.api.clickController

class BClickController {

    private var currentClickMode: BClickMode? = null

    fun changeClickMode(clickMode: BClickMode): Boolean {
        val isEmptyMode = this.currentClickMode == null
        if (isEmptyMode) {
            this.currentClickMode = clickMode
        } else {
            if (this.currentClickMode!!.finish(clickMode)) {
                this.currentClickMode = null
            }
        }
        return isEmptyMode
    }

    fun forceChangeClickMode(clickMode: BClickMode) {
        this.currentClickMode = clickMode
    }
}