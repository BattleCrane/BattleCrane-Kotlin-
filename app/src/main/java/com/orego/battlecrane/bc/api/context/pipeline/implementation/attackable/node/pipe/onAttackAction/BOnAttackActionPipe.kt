package com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.BAttackablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.node.BOnAttackActionNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe

class BOnAttackActionPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_ATTACK_ACTION_PIPE"

        fun createEvent(attackableId: Long) = Event(attackableId)
    }

    override val name = NAME

    init {
        this.placeNode(BOnAttackActionNode(context))
    }

    open class Event(val attackableId: Long) : BAttackablePipe.Event()
}