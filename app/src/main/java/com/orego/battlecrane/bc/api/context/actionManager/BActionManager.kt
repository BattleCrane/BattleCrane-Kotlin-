package com.orego.battlecrane.bc.api.context.actionManager

import com.orego.battlecrane.bc.api.model.action.BAction

class BActionManager {

    val actionHeap = mutableMapOf<Long, BAction>()
}