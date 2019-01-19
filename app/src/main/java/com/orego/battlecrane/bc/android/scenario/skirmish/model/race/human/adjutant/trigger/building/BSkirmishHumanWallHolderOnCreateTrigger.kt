package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.adjutant.trigger.building

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.standardImpl.race.human.adjutant.BHumanAdjutantHolder
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.adjutant.trigger.building.BSkirmishHumanWallOnCreateTrigger

class BSkirmishHumanWallHolderOnCreateTrigger private constructor(
    private val uiGameContext: BUiGameContext, val holder: BHumanAdjutantHolder
) : BNode(uiGameContext.gameContext) {

    override fun handle(event: BEvent): BEvent? {
        if (event is BSkirmishHumanWallOnCreateTrigger.Event && event.playerId == this.holder.item.playerId) {
            this.uiGameContext.uiPipe.addAnimation {
                val mapController = this.context.mapController
                val unitFactory = this.uiGameContext.uiUnitFactory
                val x = event.x
                val y = event.y
                val wall1 = mapController.getUnitByPosition(this.context, x, y)
                val wall2 = mapController.getUnitByPosition(
                    this.context, x, y + BSkirmishHumanWallOnCreateTrigger.NEXT_WALL_POSITION
                )
                unitFactory.build(this.uiGameContext, wall1)
                unitFactory.build(this.uiGameContext, wall2)
            }
        }
        return null
    }

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val holder = this@BSkirmishHumanWallHolderOnCreateTrigger.holder
    }

    companion object {

        fun connect(uiGameContext: BUiGameContext, holder: BHumanAdjutantHolder) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BSkirmishHumanWallOnCreateTrigger && node.playerId == holder.item.playerId
            }
            val uiTrigger = BSkirmishHumanWallHolderOnCreateTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}