package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.BAttackNode
import com.orego.battlecrane.bc.api.model.contract.BAttackable

class BAttackPipe(context: BGameContext) : BEventPipeline.Pipe(context) {

    companion object {

        const val NAME = "ATTACK_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<Node>(BAttackNode(context))

    open class AttackBundle(val attackable : BAttackable)
}