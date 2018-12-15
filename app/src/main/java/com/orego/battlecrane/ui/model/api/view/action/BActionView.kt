package com.orego.battlecrane.ui.model.api.view.action

import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.ui.model.api.view.BView

abstract class BActionView(entity: BAction) : BView<BAction>(entity) {

    lateinit var position: BPoint
}