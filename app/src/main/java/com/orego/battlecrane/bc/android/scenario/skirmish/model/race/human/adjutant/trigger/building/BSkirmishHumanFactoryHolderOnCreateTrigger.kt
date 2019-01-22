package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.adjutant.trigger.building

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.standardImpl.race.human.adjutant.BHumanAdjutantHolder
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.adjutant.trigger.building.BSkirmishHumanFactoryOnCreateTrigger

class BSkirmishHumanFactoryHolderOnCreateTrigger private constructor(
    private val uiGameContext: BUiGameContext, val holder: BHumanAdjutantHolder
) : BNode(uiGameContext.gameContext) {

    override fun handle(event: BEvent): BEvent? {
        if (event is BSkirmishHumanFactoryOnCreateTrigger.Event && event.playerId == this.holder.item.playerId) {
            this.uiGameContext.uiTaskManager.addTask {
                val gameContext = this.uiGameContext.gameContext
                val factory = gameContext.mapController.getUnitByPosition(gameContext, event.x, event.y)
                this.uiGameContext.uiUnitFactory.build(this.uiGameContext, factory)
            }
        }
        return null
    }

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val holder = this@BSkirmishHumanFactoryHolderOnCreateTrigger.holder
    }

    companion object {

        fun connect(uiGameContext: BUiGameContext, holder: BHumanAdjutantHolder) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BSkirmishHumanFactoryOnCreateTrigger && node.playerId == holder.item.playerId
            }
            val uiTrigger = BSkirmishHumanFactoryHolderOnCreateTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}