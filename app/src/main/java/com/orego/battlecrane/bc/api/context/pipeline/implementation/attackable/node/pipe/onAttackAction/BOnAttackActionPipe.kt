package com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.BAttackablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.node.BOnAttackActionNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe

@BContextComponent
class BOnAttackActionPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_ATTACK_ACTION_PIPE"

        fun createEvent(attackableId: Long, hitPointableId: Long)
                = Event(attackableId, hitPointableId)
    }

    override val name = NAME

    init {
        this.placeNode(BOnAttackActionNode(context))
    }

    open class Event(val attackableId: Long, val hitPointableId: Long) : BAttackablePipe.Event()
}