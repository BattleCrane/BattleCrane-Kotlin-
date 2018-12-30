package com.orego.battlecrane.bc.std.race.human.unit

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.model.annotation.unitComponent.BUnitComponent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.pipe.onTurnFinished.node.BOnTurnFinishedNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.turn.node.pipe.onTurnStarted.node.BOnTurnStartedNode
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.BHumanRace

abstract class BHumanUnit(context: BGameContext, ownerId: Long) : BUnit(ownerId), BHumanRace {

    protected val onTurnStartedPipeId: Long

    protected val onTurnFinishedPipeId: Long

    init {
        //Get pipeline:
        val pipeline = context.pipeline
        //Create pipes:
        val onTurnStartedPipe = BPipe(
            context, mutableListOf(OnTurnStartedNode(context, ownerId, this.unitId))
        )
        val onTurnFinishedPipe = BPipe(
            context, mutableListOf(OnTurnFinishedNode(context, ownerId, this.unitId))
        )
        //Save pipe ids:
        this.onTurnStartedPipeId = onTurnStartedPipe.id
        this.onTurnFinishedPipeId = onTurnFinishedPipe.id
        //Bind pipes:
        pipeline.bindPipeTo(BOnTurnStartedNode.NAME, onTurnStartedPipe)
        pipeline.bindPipeTo(BOnTurnFinishedNode.NAME, onTurnFinishedPipe)
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