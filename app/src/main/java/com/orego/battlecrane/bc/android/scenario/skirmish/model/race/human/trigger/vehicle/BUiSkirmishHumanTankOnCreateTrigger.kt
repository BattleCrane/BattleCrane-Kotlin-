package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.trigger.vehicle

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.util.pipe.BParentPipe
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.trigger.vehicle.BSkirmishHumanTankOnCreateTrigger

class BUiSkirmishHumanTankOnCreateTrigger private constructor(
    private val uiGameContext: BUiGameContext, val playerId: Long
) : BNode(uiGameContext.gameContext) {

    override fun handle(event: BEvent): BEvent? {
        if (event is BSkirmishHumanTankOnCreateTrigger.Event && event.playerId == this.playerId) {
            this.uiGameContext.uiTaskManager.addTask {
                val gameContext = this.uiGameContext.gameContext
                val tank = gameContext.mapController.getUnitByPosition(event.x, event.y)
                this.uiGameContext.uiUnitFactory.build(this.uiGameContext, tank)
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
                node is BSkirmishHumanTankOnCreateTrigger && node.playerId == playerId
            }
            val uiTrigger = BUiSkirmishHumanTankOnCreateTrigger(uiGameContext, playerId)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}