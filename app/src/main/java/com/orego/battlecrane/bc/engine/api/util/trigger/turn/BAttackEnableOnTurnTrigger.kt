package com.orego.battlecrane.bc.engine.api.util.trigger.turn

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.BOnAttackEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.BTurnNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BAttackableHeap
import com.orego.battlecrane.bc.engine.api.model.property.BAttackable

class BAttackEnableOnTurnTrigger private constructor(context: BGameContext, var attackable: BAttackable) :
    BOnTurnTrigger(context) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    private val attckableMap = context.storage.getHeap(BAttackableHeap::class.java).objectMap

    override var playerId: Long
        get() = this.attackable.playerId
        set(value) {
            this.attackable.playerId = value
        }

    override fun onTurnStarted() {
        this.pipeline.pushEvent(
            BOnAttackEnablePipe.Event(this.attackable.attackableId, true)
        )
    }

    override fun onTurnFinished() {
        this.pipeline.pushEvent(
            BOnAttackEnablePipe.Event(this.attackable.attackableId, false)
        )
    }

    override fun isFinished() = !this.attckableMap.containsKey(this.attackable.attackableId)

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val attackable = this@BAttackEnableOnTurnTrigger.attackable

        override fun isFinished() = this@BAttackEnableOnTurnTrigger.isFinished()
    }

    companion object {

        fun connect(context: BGameContext, attackable: BAttackable) {
            BTurnNode.connect(context) {
                BAttackEnableOnTurnTrigger(
                    context,
                    attackable
                )
            }
        }
    }
}