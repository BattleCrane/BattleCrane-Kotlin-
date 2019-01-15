package com.orego.battlecrane.ui.model.api.context.clickController

interface BClickMode {

    fun finish(nextClickMode: BClickMode): Boolean
}