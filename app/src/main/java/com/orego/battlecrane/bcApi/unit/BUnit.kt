package com.orego.battlecrane.bcApi.unit

import com.orego.battlecrane.bcApi.manager.BGameManager
import com.orego.battlecrane.bcApi.manager.battleMapManager.cell.BCell

abstract class BUnit(protected val gameManager : BGameManager, var ownerId : Int) {

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