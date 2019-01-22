package com.orego.battlecrane.bc.engine.api.util.trigger.attack

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.BOnAttackEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.node.BOnAttackEnableNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.model.property.BAttackable

class BOnAttackEnableTrigger private constructor(context: BGameContext, var attackable: BAttackable) : BNode(context) {

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnAttackEnablePipe.Event
            && this.attackable.attackableId == event.attackableId
            && event.isEnable(this.context)
        ) {
            println("ATTACKABLE: $attackable")
            event.perform(this.context)
            return this.pushToInnerPipes(event)
        }
        return null
    }

    companion object {

        fun connect(context: BGameContext, attackable: BAttackable) {
            BOnAttackEnableNode.connect(context) {
                BOnAttackEnableTrigger(context, attackable)
            }
        }
    }
}