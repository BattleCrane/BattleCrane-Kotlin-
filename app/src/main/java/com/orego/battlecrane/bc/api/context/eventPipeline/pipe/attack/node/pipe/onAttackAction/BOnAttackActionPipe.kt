package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackAction

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.BAttackNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackAction.node.BOnAttackFinishedNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackAction.node.BOnAttackStartedNode
import com.orego.battlecrane.bc.api.model.contract.BAttackable
import com.orego.battlecrane.bc.api.model.contract.BHitPointable

class BOnAttackActionPipe(context: BGameContext) : BEventPipeline.Pipe(context) {

    companion object {

        const val NAME = "${BAttackNode.NAME}/ON_ATTACK_ACTION_PIPE"
    }

    override val name =
        NAME

    override val nodes = mutableListOf<Node>(BOnAttackStartedNode(context), BOnAttackFinishedNode(context))

    open class OnAttackActionBundle(val attackable : BAttackable, val target : BHitPointable)
}