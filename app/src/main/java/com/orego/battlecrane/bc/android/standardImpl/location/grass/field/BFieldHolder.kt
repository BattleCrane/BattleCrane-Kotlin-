package com.orego.battlecrane.bc.android.standardImpl.location.grass.field

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.clickController.BClickMode
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.BGrassField

abstract class BFieldHolder(uiGameContext: BUiGameContext, item: BGrassField) :
    BUnitHolder(uiGameContext, item) {

    abstract fun getItemPath() : String

    /**
     * Click mode.
     */

    inner class ClickMode : BUnitHolder.ClickMode(this) {

        override fun handle(nextClickMode: BClickMode) = nextClickMode
    }
}