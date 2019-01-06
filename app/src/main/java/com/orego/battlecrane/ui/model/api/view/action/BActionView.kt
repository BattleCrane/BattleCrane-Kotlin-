package com.orego.battlecrane.ui.model.api.view.action

import com.orego.battlecrane.bc.api.context.controller.map.point.BPoint
import com.orego.battlecrane.bc.api.model.entity.main.BAction
import com.orego.battlecrane.ui.model.api.uiGameContext.clickController.BClickMode
import com.orego.battlecrane.ui.model.api.view.BView

abstract class BActionView(entity: BAction) : BView<BAction>(entity) {

    lateinit var stackPosition: BPoint

    abstract inner class ClickMode : BClickMode
}