package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackEnable

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.BAttackPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.BAttackNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackEnable.node.BOnAttackEnableNode
import com.orego.battlecrane.bc.api.model.contract.BAttackable

class BOnAttackEnablePipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "${BAttackNode.NAME}/ON_ATTACK_ENABLE_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<BNode>(BOnAttackEnableNode(context))

    open class AttackEnableBundle(attackable: BAttackable, val isEnable: Boolean) :
        BAttackPipe.AttackBundle(attackable)
}