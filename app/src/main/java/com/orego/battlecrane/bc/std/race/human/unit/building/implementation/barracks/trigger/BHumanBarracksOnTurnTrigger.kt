package com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.BTurnNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.BHumanBarracks

@BUnitComponent
class BHumanBarracksOnTurnTrigger(context: BGameContext, val barracks: BHumanBarracks) :
    BNode(context) {

    companion object {

        fun connect(context: BGameContext, barracks: BHumanBarracks) {
            val pipe = BHumanBarracksOnTurnTrigger(context, barracks).intoPipe()
            context.pipeline.bindPipeToNode(BTurnNode.NAME, pipe)
        }
    }

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

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val barracks = this@BHumanBarracksOnTurnTrigger.barracks

        override fun isUnused() = this@BHumanBarracksOnTurnTrigger.isUnused()
    }
}