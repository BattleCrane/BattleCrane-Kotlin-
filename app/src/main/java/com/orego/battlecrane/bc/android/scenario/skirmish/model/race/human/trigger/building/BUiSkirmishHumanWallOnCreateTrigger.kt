package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.trigger.building

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.util.pipe.BParentPipe
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.trigger.building.BSkirmishHumanWallOnCreateTrigger

class BUiSkirmishHumanWallOnCreateTrigger private constructor(
    private val uiGameContext: BUiGameContext, val playerId: Long
) : BNode(uiGameContext.gameContext) {

    override fun handle(event: BEvent): BEvent? {
        if (event is BSkirmishHumanWallOnCreateTrigger.Event && event.playerId == this.playerId) {
            this.uiGameContext.uiTaskManager.addTask {
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

    inner class Pipe : BParentPipe(this)

    companion object {

        fun connect(uiGameContext: BUiGameContext, playerId: Long) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BSkirmishHumanWallOnCreateTrigger && node.playerId == playerId
            }
            val uiTrigger = BUiSkirmishHumanWallOnCreateTrigger(uiGameContext, playerId)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}