package bcApi.manager.mapManager.cell

import bcApi.unit.BUnit

data class BCell(val x: Int, val y: Int) {
    var attachedUnit: BUnit? = null
}