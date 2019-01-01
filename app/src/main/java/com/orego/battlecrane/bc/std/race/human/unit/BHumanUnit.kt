package com.orego.battlecrane.bc.std.race.human.unit

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BPlayerHeap
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.entity.main.BUnit
import com.orego.battlecrane.bc.std.race.human.BHumanRace

abstract class BHumanUnit(context: BGameContext, playerId: Long, x: Int, y: Int) :
    BUnit(context, playerId, x, y), BHumanRace {

    /**
     * Context.
     */

    val onTurnStartedPipeId: Long

    val onTurnStartedNodeId: Long

    val onTurnFinishedPipeId: Long

    val onTurnFinishedNodeId: Long

    init {
        //Get context:
        val pipeline = context.pipeline
        val playerHeap = context.storage.getHeap(BPlayerHeap::class.java)
        val player = playerHeap[playerId]

        //On turn started:
        val onTurnStartedNode = OnTurnStartedNode(context, playerId)
        val onTurnStartedPipe = onTurnStartedNode.wrapInPipe()

        //On turn finished:
        val onTurnFinishedNode = OnTurnFinishedNode(context, playerId)
        val onTurnFinishedPipe = onTurnFinishedNode.wrapInPipe()

        //Save pipe ids:
        this.onTurnStartedPipeId = onTurnStartedPipe.id
        this.onTurnStartedNodeId = onTurnStartedNode.id
        this.onTurnFinishedPipeId = onTurnFinishedPipe.id
        this.onTurnFinishedNodeId = onTurnFinishedNode.id

        //Bind pipes:
        pipeline.bindPipeToNode(player.onTurnStartedNodeId, onTurnStartedPipe)
        pipeline.bindPipeToNode(player.onTurnFinishedNodeId, onTurnFinishedPipe)
    }

    @BUnitComponent
    class OnTurnStartedNode(context: BGameContext, unitId: Long) :
        BNode(context) {

        private val humanUnit = this.context.storage.getHeap(BUnitHeap::class.java)[unitId]!! as BHumanUnit

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnTurnStartedPipe.Event
                && this.humanUnit.playerId == event.playerId
            ) {
                return this.pushEventIntoPipes(event)
            }
            return null
        }
    }

    @BUnitComponent
    class OnTurnFinishedNode(context: BGameContext, unitId: Long) :
        BNode(context) {

        private val humanUnit = this.context.storage.getHeap(BUnitHeap::class.java)[unitId]!! as BHumanUnit

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnTurnFinishedPipe.Event
                && this.humanUnit.playerId == event.playerId
            ) {
                return this.pushEventIntoPipes(event)
            }
            return null
        }
    }
}