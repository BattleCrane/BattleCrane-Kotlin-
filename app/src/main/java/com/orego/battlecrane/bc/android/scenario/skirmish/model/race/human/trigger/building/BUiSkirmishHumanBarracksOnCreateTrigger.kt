package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.trigger.building

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.util.pipe.BParentPipe
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.trigger.building.BSkirmishHumanBarracksOnCreateTrigger

class BUiSkirmishHumanBarracksOnCreateTrigger private constructor(
    private val uiGameContext: BUiGameContext, val playerId: Long
) : BNode(uiGameContext.gameContext) {

    override fun handle(event: BEvent): BEvent? {
        if (event is BSkirmishHumanBarracksOnCreateTrigger.Event && event.playerId == this.playerId) {
            this.uiGameContext.uiTaskManager.addTask {
                val gameContext = this.uiGameContext.gameContext
                val barracks = gameContext.mapController.getUnitByPosition(gameContext, event.x, event.y)
                this.uiGameContext.uiUnitFactory.build(this.uiGameContext, barracks)
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
                node is BSkirmishHumanBarracksOnCreateTrigger && node.playerId == playerId
            }
            val uiTrigger = BUiSkirmishHumanBarracksOnCreateTrigger(uiGameContext, playerId)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}