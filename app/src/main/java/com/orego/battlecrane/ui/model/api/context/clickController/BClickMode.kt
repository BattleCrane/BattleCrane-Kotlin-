package com.orego.battlecrane.ui.model.api.context.clickController

interface BClickMode {

    fun finish(otherClickMode: BClickMode): Boolean
}