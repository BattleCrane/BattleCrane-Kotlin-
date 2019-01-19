package com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.BAttackablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackAction.node.BOnAttackActionNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe

class BOnAttackActionPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_ATTACK_ACTION_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BOnAttackActionNode(context))
    }

    open class Event protected constructor(val attackableId: Long) : BAttackablePipe.Event()
}