package bcApi.unit

import bcApi.manager.BGameManager
import bcApi.manager.mapManager.cell.BCell
import bcApi.manager.playerManager.team.player.BPlayer

abstract class BUnit(
    protected val gameManager : BGameManager,
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