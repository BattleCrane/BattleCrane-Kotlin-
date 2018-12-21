package com.orego.battlecrane.bc.api.model.unit

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.util.BIdGenerator

abstract class BUnit(
    protected val context: BGameContext,
    var owner: BPlayer? = null
) {

    var pivot: BPoint? = null

    val unitId = BIdGenerator.generateUnitId()

    abstract val verticalSize: Int

    abstract val horizontalSize: Int

    abstract fun isPlaced(position: BPoint): Boolean

    /**
     * Unit lifecycle.
     */

    open fun onCreate() {

    }

    open fun onTurnStarted() {

    }

    open fun onTurnEnded() {

    }

    open fun onDestroy() {

    }
}