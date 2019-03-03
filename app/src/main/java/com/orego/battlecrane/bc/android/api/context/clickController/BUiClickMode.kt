package com.orego.battlecrane.bc.android.api.context.clickController

/**
 * Defines a click states.
 */

interface BUiClickMode {

    /**
     * Starts if controller mode is empty
     */

    fun onStartClickMode() {}

    /**
     * Handles next click controller.
     */

    fun onNextClickMode(nextUiClickMode: BUiClickMode?): BUiClickMode? =
        nextUiClickMode.also { it?.onStartClickMode() }
}