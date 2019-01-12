package com.orego.battlecrane.ui.model.api.clickController

interface BClickMode {

    fun finish(nextClickMode: BClickMode): Boolean
}