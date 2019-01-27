package com.orego.battlecrane.bc.engine.api.util.trigger.attack

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.BOnAttackEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.node.BOnAttackEnableNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BAttackableHeap
import com.orego.battlecrane.bc.engine.api.model.property.BAttackable
import com.orego.battlecrane.bc.engine.api.util.pipe.BParentPipe

class BOnAttackEnableTrigger private constructor(context: BGameContext, var attackable: BAttackable) : BNode(context) {

    /**
     * Context.
     */

    private val attackableMap = context.storage.getHeap(BAttackableHeap::class.java).objectMap

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

    override fun intoPipe() = Pipe()

    override fun isFinished() = !this.attackableMap.containsKey(this.attackable.attackableId)

    /**
     * Pipe.
     */

    inner class Pipe : BParentPipe(this)

    companion object {

        fun connect(context: BGameContext, attackable: BAttackable) {
            BOnAttackEnableNode.connect(context) {
                BOnAttackEnableTrigger(context, attackable)
            }
        }
    }
}