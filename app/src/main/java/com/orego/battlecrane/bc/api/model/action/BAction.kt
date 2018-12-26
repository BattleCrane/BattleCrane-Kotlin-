package com.orego.battlecrane.bc.api.model.action

import com.orego.battlecrane.bc.api.context.BGameContext

abstract class BAction(var ownerId: Long = 0) {

    abstract fun perform(context: BGameContext): Boolean
}