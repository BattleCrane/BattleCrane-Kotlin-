package com.orego.battlecrane.bc.api.model.contract

import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint

interface BTargetable {

    var targetPosition: BPoint?
}