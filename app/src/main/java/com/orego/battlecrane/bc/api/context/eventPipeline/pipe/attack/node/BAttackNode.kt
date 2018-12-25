package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.BAttackPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackAction.BOnAttackActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackEnable.BOnAttackEnablePipe

class BAttackNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "ATTACK_NODE"
    }

    override val name = NAME

    init {
        //Put on attack action node:
        this.pipeMap[BOnAttackActionPipe.NAME] = BOnAttackActionPipe(context)
        //Put on attack enable node:
        this.pipeMap[BOnAttackEnablePipe.NAME] = BOnAttackEnablePipe(context)
    }

    override fun handle(event: BEvent) : BEvent? {
        return if (event.bundle is BAttackPipe.AttackBundle) {
            this.pipeMap.values.forEach { it.push(event) }
            event
        } else {
            null
        }
    }
}