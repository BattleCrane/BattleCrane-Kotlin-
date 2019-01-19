package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.barracks.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.BTurnNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanBarracks

class BSkirmishHumanBarracksOnTurnTrigger private constructor(context: BGameContext, val barracks: BHumanBarracks) :
    BNode(context) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    private val unitMap = context.storage.getHeap(BUnitHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        if (event is BTurnPipe.Event && this.barracks.playerId == event.playerId) {
            val producableId = this.barracks.producableId
            when (event) {
                is BOnTurnStartedPipe.Event -> {
                    this.pushToInnerPipes(event)
                    this.pipeline.pushEvent(
                        BOnProduceEnablePipe.createEvent(producableId, true)
                    )
                    return event
                }
                is BOnTurnFinishedPipe.Event -> {
                    this.pushToInnerPipes(event)
                    this.pipeline.pushEvent(
                        BOnProduceEnablePipe.createEvent(producableId, false)
                    )
                    return event
                }
            }
        }
        return null
    }

    override fun intoPipe() = Pipe()

    override fun isUnused() = !this.unitMap.containsKey(this.barracks.unitId)

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val barracks = this@BSkirmishHumanBarracksOnTurnTrigger.barracks

        override fun isUnused() = this@BSkirmishHumanBarracksOnTurnTrigger.isUnused()
    }

    companion object {

        fun connect(context: BGameContext, barracks: BHumanBarracks) {
            BTurnNode.connect(context) {
                BSkirmishHumanBarracksOnTurnTrigger(
                    context,
                    barracks
                )
            }
        }
    }
}