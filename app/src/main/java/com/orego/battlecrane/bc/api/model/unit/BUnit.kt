package com.orego.battlecrane.bc.api.model.unit

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.util.BIdGenerator

abstract class BUnit(
    protected val context: BGameContext,
    var owner: BPlayer? = null
) {

    val unitId = BIdGenerator.generateUnitId()

    var pivot: BPoint? = null

    abstract val verticalSide: Int

    abstract val horizontalSide: Int

    abstract fun isPlaced(position: BPoint): Boolean

    open fun onTurnStarted() {

    }

    open fun onTurnEnded() {

    }
}