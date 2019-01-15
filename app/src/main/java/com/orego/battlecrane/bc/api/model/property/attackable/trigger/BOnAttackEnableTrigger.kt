package com.orego.battlecrane.bc.api.model.property.attackable.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.BOnAttackEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.node.BOnAttackEnableNode
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.model.property.attackable.BAttackable

class BOnAttackEnableTrigger(context: BGameContext, var attackable: BAttackable) : BNode(context) {

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnAttackEnablePipe.Event
            && this.attackable.attackableId == event.attackableId
            && event.isEnable(this.context)
        ) {
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