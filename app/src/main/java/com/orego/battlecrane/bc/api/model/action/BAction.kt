package com.orego.battlecrane.bc.api.model.action

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.util.BIdGenerator

abstract class BAction(var ownerId: Long = 0) {

    val actionId : Long = BIdGenerator.generateActionId()

    abstract fun perform(context: BGameContext): Boolean
}