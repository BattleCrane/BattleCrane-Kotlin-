package com.orego.battlecrane.ui.model.api.view.unit

import com.orego.battlecrane.bc.api.model.entity.main.BUnit
import com.orego.battlecrane.ui.model.api.modeController.BClickMode
import com.orego.battlecrane.ui.model.api.view.BView

abstract class BUnitView(entity: BUnit) : BView<BUnit>(entity) {

    abstract inner class ClickMode : BClickMode {

        fun getUnit() = this@BUnitView.entity
    }
}