package com.orego.battlecrane.bc.android.api.context.clickController

/**
 * Defines a click states.
 */

open class BUiClickMode {

    var previousMode : BUiClickMode? = null

    /**
     * Starts if controller mode is empty
     */

    open fun onStartClickMode() {}

    /**
     * Handles next click controller.
     */

    open fun onNextClickMode(nextUiClickMode: BUiClickMode?): BUiClickMode? {
        if (nextUiClickMode != null) {
            nextUiClickMode.previousMode = this
            nextUiClickMode.onStartClickMode()
        }
        return nextUiClickMode
    }
}