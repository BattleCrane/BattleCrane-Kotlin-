package com.orego.battlecrane.bc.api.model.unit

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.util.BIdGenerator

abstract class BUnit(var ownerId: Long = 0) {

    val unitId = BIdGenerator.generateUnitId()

    var pivot: BPoint? = null

    abstract val verticalSize: Int

    abstract val horizontalSize: Int

    abstract fun isPlaced(context: BGameContext, position: BPoint): Boolean
}