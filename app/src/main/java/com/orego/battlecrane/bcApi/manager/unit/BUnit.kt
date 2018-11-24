package com.orego.battlecrane.bcApi.manager.unit

import com.orego.battlecrane.bcApi.manager.battlefield.cell.BCell

abstract class BUnit {

    val id = BUnitIdGenerator.generateUnitId()

    var pivot: BCell? = null

    abstract val verticalSide: Int

    abstract val horizontalSide: Int
}