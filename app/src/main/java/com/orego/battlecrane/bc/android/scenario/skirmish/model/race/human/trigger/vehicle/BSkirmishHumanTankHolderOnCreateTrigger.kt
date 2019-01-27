package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.trigger.vehicle

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.trigger.vehicle.BSkirmishHumanTankOnCreateTrigger

class BSkirmishHumanTankHolderOnCreateTrigger private constructor(
    private val uiGameContext: BUiGameContext, val holder: BHumanAdjutantHolder
) : BNode(uiGameContext.gameContext) {

    override fun handle(event: BEvent): BEvent? {
        if (event is BSkirmishHumanTankOnCreateTrigger.Event && event.playerId == this.holder.item.playerId) {
            this.uiGameContext.uiTaskManager.addTask {
                val gameContext = this.uiGameContext.gameContext
                val tank = gameContext.mapController.getUnitByPosition(gameContext, event.x, event.y)
                this.uiGameContext.uiUnitFactory.build(this.uiGameContext, tank)
            }
        }
        return null
    }

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val holder = this@BSkirmishHumanTankHolderOnCreateTrigger.holder
    }

    companion object {

        fun connect(uiGameContext: BUiGameContext, holder: BHumanAdjutantHolder) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BSkirmishHumanTankOnCreateTrigger && node.playerId == holder.item.playerId
            }
            val uiTrigger = BSkirmishHumanTankHolderOnCreateTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}