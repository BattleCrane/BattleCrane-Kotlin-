package com.orego.battlecrane.bc.std.race.human.adjutant

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.node.BOnCreateUnitNode
import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.std.race.human.BHumanRace
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanBarracks
import com.orego.battlecrane.bc.std.race.human.unit.infantry.implementation.BHumanMarine

class BHumanAdjutant(context: BGameContext, playerId: Long) : BAdjutant(context, playerId), BHumanRace {

    override var isAble = true

    /**
     * Context.
     */

    val onCreateBarracksPipeId: Long

    val onCreateBarracksNodeId: Long

    val onCreateMarinePipeId: Long

    val onCreateMarineNodeId: Long

    init {
        //Get pipeline:
        val pipeline = context.pipeline

        //Barracks:
        val onCreateBarracksNode = BHumanBarracks.OnCreateNode(context, playerId)
        val onCreateBarracksPipe = onCreateBarracksNode.wrapInPipe()
        this.onCreateBarracksNodeId = onCreateBarracksNode.id
        this.onCreateBarracksPipeId = onCreateBarracksPipe.id
        pipeline.bindPipeToNode(BOnCreateUnitNode.NAME, onCreateBarracksPipe)

        //Marine:
        val onCreateMarineNode = BHumanMarine.OnCreateNode(context, playerId)
        val onCreateMarinePipe = onCreateMarineNode.wrapInPipe()
        this.onCreateMarineNodeId = onCreateMarineNode.id
        this.onCreateMarinePipeId = onCreateMarinePipe.id
        pipeline.bindPipeToNode(BOnCreateUnitNode.NAME, onCreateMarinePipe)
    }
}