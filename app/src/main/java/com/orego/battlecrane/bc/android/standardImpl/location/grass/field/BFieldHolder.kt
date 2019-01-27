package com.orego.battlecrane.bc.android.standardImpl.location.grass.field

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BUiClickMode
import com.orego.battlecrane.bc.android.api.model.unit.BUnitHolder
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.BGrassField

abstract class BFieldHolder(uiGameContext: BUiGameContext, item: BGrassField) :
    BUnitHolder(uiGameContext, item) {

    abstract fun getItemPath() : String

    /**
     * Click mode.
     */

    inner class UiClickMode : BUnitHolder.UiClickMode(this) {

        override fun onNextClickMode(nextUiClickMode: BUiClickMode) = nextUiClickMode
    }
}