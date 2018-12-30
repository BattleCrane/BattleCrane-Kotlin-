package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.attack.node.pipe.onAttackAction

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.attack.BAttackPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.attack.node.BAttackNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.attack.node.pipe.onAttackAction.node.BOnAttackActionNode
import com.orego.battlecrane.bc.api.model.contract.BAttackable
import com.orego.battlecrane.bc.api.model.contract.BHitPointable

@BContextComponent
class BOnAttackActionPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_ATTACK_ACTION_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<BNode>(BOnAttackActionNode(context))

    open class OnAttackActionEvent(
        val attackableId: Long,
        val hitPointableId: Long
    ) : BAttackPipe.AttackableEvent()
}