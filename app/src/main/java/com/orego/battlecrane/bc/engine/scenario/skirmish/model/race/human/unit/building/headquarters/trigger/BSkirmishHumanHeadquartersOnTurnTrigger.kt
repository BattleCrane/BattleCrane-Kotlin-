package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.headquarters.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.BTurnNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.engine.api.util.trigger.turn.BOnTurnTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanHeadquarters

class BSkirmishHumanHeadquartersOnTurnTrigger private constructor(
    context: BGameContext,
    var headquarters: BHumanHeadquarters
) : BOnTurnTrigger(context) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    private val unitMap = context.storage.getHeap(BUnitHeap::class.java).objectMap

    override var playerId: Long
        get() = this.headquarters.playerId
        set(value) {
            this.headquarters.playerId = value
        }

    override fun onTurnStarted() {
        if (this.isBattleMode()) {
            this.makeAttack()
        }
        this.pipeline.pushEvent(
            BOnProduceEnablePipe.Event(this.headquarters.producableId, true)
        )
    }

    override fun onTurnFinished() {
        this.pipeline.pushEvent(
            BOnProduceEnablePipe.Event(this.headquarters.producableId, false)
        )
    }

    private fun isBattleMode(): Boolean {
        //TODO
        return false
    }

    private fun makeAttack() {
        //TODO
    }

    override fun isFinished() = !this.unitMap.containsKey(this.headquarters.unitId)

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        var headquarters = this@BSkirmishHumanHeadquartersOnTurnTrigger.headquarters

        override fun isFinished() = this@BSkirmishHumanHeadquartersOnTurnTrigger.isFinished()
    }

    companion object {

        fun connect(context: BGameContext, headquarters: BHumanHeadquarters) {
            BTurnNode.connect(context) {
                BSkirmishHumanHeadquartersOnTurnTrigger(context, headquarters)
            }
        }
    }
}