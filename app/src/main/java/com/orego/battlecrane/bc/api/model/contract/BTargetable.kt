package com.orego.battlecrane.bc.api.model.contract

import com.orego.battlecrane.bc.api.context.controller.map.point.BPoint

interface BTargetable {

    var targetPosition: BPoint?
}