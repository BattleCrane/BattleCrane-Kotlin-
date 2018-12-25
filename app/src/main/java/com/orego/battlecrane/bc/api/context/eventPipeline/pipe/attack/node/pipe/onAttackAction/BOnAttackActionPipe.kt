package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackAction

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.BAttackPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.BAttackNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackAction.node.BOnAttackActionNode
import com.orego.battlecrane.bc.api.model.contract.BAttackable
import com.orego.battlecrane.bc.api.model.contract.BHitPointable

class BOnAttackActionPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "${BAttackNode.NAME}/ON_ATTACK_ACTION_PIPE"

        const val EVENT = "ON_ATTACK_ACTION"
    }

    override val name = NAME

    override val nodes = mutableListOf<BNode>(BOnAttackActionNode(context))

    open class OnAttackActionBundle(attackable : BAttackable, val target : BHitPointable) :
        BAttackPipe.AttackBundle(attackable)
}