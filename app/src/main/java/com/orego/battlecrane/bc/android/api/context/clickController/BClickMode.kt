package com.orego.battlecrane.bc.android.api.context.clickController

interface BClickMode {

    fun handle(nextClickMode: BClickMode): BClickMode?
}