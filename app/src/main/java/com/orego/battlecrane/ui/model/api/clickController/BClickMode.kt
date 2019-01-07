package com.orego.battlecrane.ui.model.api.clickController

interface BClickMode {

    fun finish(otherClickMode: BClickMode): Boolean
}