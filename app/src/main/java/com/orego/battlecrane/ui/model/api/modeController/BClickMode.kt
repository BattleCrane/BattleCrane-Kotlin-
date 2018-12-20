package com.orego.battlecrane.ui.model.api.modeController

interface BClickMode {

    fun finish(otherClickMode: BClickMode): Boolean
}