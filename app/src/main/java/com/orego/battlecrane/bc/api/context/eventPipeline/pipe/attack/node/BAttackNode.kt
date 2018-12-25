package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate.BOnCreateActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onPerform.BOnPerformActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.BAttackPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackAction.BOnAttackActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.node.pipe.onAttackEnable.BOnAttackEnablePipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.BUnitPipe

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
        return if (event.any is BAttackPipe.AttackBundle) {
            this.pipeMap.values.forEach { it.push(event) }
            event
        } else {
            null
        }
    }
}