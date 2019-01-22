package com.orego.battlecrane.bc.android.api.context.clickController

interface BClickMode {

    fun onStart() {}

    fun onNext(nextClickMode: BClickMode): BClickMode? = nextClickMode
}