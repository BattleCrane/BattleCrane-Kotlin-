package com.orego.battlecrane.ui.model.std.location.grass.field

import android.view.View
import com.orego.battlecrane.bc.std.location.grass.field.BGrassField
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.context.clickController.BClickMode
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder

abstract class BFieldHolder(uiGameContext: BUiGameContext, item: BGrassField) :
    BUnitHolder(uiGameContext, item) {

    final override val unitView: View = BUnitHolder.placeImageView(uiGameContext, item, this.getItemPath())

    init {
        this.unitView.setOnClickListener {
            uiGameContext.clickController.pushClickMode(ClickMode())
        }
    }

    protected abstract fun getItemPath() : String

    /**
     * Click mode.
     */

    inner class ClickMode : BUnitHolder.ClickMode(this) {

        override fun handle(nextClickMode: BClickMode) = nextClickMode
    }
}