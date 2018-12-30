package com.orego.battlecrane.bc.std.race.human.unit

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.annotation.unitComponent.BUnitComponent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.BHumanRace

abstract class BHumanUnit(context: BGameContext, ownerId: Long) : BUnit(ownerId), BHumanRace {

    /**
     * Context.
     */

    var onTurnStartedPipeId: Long

    var onTurnStartedNodeId : Long

    var onTurnFinishedPipeId: Long

    var onTurnFinishedNodeId : Long

    init {
        //Get pipeline:
        val pipeline = context.pipeline
        //On turn started:
        val onTurnStartedNode = OnTurnStartedNode(context, ownerId, this.unitId)
        val onTurnStartedPipe = onTurnStartedNode.wrapInPipe()
        //On turn finished:
        val onTurnFinishedNode = OnTurnFinishedNode(context, ownerId, this.unitId)
        val onTurnFinishedPipe = onTurnFinishedNode.wrapInPipe()
        //Save pipe ids:
        this.onTurnStartedPipeId = onTurnStartedPipe.id
        this.onTurnStartedNodeId = onTurnStartedNode.id
        this.onTurnFinishedPipeId = onTurnFinishedPipe.id
        this.onTurnFinishedNodeId = onTurnFinishedNode.id
        //Bind pipes:
        val player = context.playerManager.getPlayerById(ownerId)
        val onPlayerTurnStartedNodeId = player.onTurnStartedNodeId
        val onPlayerTurnFinishedNodeId = player.onTurnFinishedNodeId
        pipeline.bindPipeToNode(onPlayerTurnStartedNodeId, onTurnStartedPipe)
        pipeline.bindPipeToNode(onPlayerTurnFinishedNodeId, onTurnFinishedPipe)
    }

    @BUnitComponent
    class OnTurnStartedNode(context: BGameContext, private val ownerId: Long, private val unitId: Long) :
        BNode(context) {

        override fun handle(event: BEvent): BEvent? {
            return if (event is BOnTurnStartedPipe.OnTurnStartedEvent
                && event.ownerId == this.ownerId
            ) {
                this.pipeMap.values.forEach { it.push(event) }
                event
            } else {
                null
            }
        }
    }

    @BUnitComponent
    class OnTurnFinishedNode(context: BGameContext, private val ownerId: Long, private val unitId: Long) :
        BNode(context) {

        override fun handle(event: BEvent): BEvent? {
            return if (event is BOnTurnFinishedPipe.OnTurnFinishedEvent
                && event.ownerId == this.ownerId
            ) {
                this.pipeMap.values.forEach { it.push(event) }
                event
            } else {
                null
            }
        }
    }
}