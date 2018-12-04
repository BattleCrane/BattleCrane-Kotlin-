package com.orego.battlecrane.bcApi.model.unit

import com.orego.battlecrane.bcApi.manager.BGameContext
import com.orego.battlecrane.bcApi.manager.mapManager.cell.BCell
import com.orego.battlecrane.bcApi.manager.playerManager.team.player.BPlayer

abstract class BUnit(
    protected val gameContext : BGameContext,
    var owner : BPlayer? = null
) {

    val unitId = BIdGenerator.generateUnitId()

    var pivot: BCell? = null

    abstract val verticalSide: Int

    abstract val horizontalSide: Int

    val onCreateObserver = mutableMapOf<Long, OnCreateListener>()

    val onDestroyListener = mutableMapOf<Long, OnDestroyListener>()

    interface OnCreateListener {

        fun onCreate(unit : BUnit)
    }

    interface OnDestroyListener {

        fun onDestroy(unit: BUnit)
    }
}