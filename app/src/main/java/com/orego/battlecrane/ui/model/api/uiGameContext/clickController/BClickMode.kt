package com.orego.battlecrane.ui.model.api.uiGameContext.clickController

interface BClickMode {

    fun finish(otherClickMode: BClickMode): Boolean
}