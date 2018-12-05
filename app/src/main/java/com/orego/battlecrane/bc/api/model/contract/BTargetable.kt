package com.orego.battlecrane.bc.api.model.contract

import com.orego.battlecrane.bc.api.manager.mapManager.cell.BCell

interface BTargetable {

    var targetPosition: BCell?
}