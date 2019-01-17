package com.orego.battlecrane.ui.model.api.context.clickController

interface BClickMode {

    fun handle(nextClickMode: BClickMode): BClickMode?
}