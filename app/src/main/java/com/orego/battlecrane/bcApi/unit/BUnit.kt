package com.orego.battlecrane.bcApi.unit

import com.orego.battlecrane.bcApi.manager.battleMapManager.cell.BCell

abstract class BUnit {

    val id = BUnitIdGenerator.generateUnitId()

    var pivot: BCell? = null

    abstract val verticalSide: Int

    abstract val horizontalSide: Int
}