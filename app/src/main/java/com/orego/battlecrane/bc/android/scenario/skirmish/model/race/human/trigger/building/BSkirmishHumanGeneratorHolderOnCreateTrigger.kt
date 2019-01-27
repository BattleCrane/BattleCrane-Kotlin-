package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.trigger.building

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.trigger.building.BSkirmishHumanGeneratorOnCreateTrigger

class BSkirmishHumanGeneratorHolderOnCreateTrigger private constructor(
    private val uiGameContext: BUiGameContext, val holder: BHumanAdjutantHolder
) : BNode(uiGameContext.gameContext) {

    override fun handle(event: BEvent): BEvent? {
        if (event is BSkirmishHumanGeneratorOnCreateTrigger.Event && event.playerId == this.holder.item.playerId) {
            this.uiGameContext.uiTaskManager.addTask {
                val gameContext = this.uiGameContext.gameContext
                val generator = gameContext.mapController.getUnitByPosition(gameContext, event.x, event.y)
                this.uiGameContext.uiUnitFactory.build(this.uiGameContext, generator)
            }
        }
        return null
    }

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val holder = this@BSkirmishHumanGeneratorHolderOnCreateTrigger.holder
    }

    companion object {

        fun connect(uiGameContext: BUiGameContext, holder: BHumanAdjutantHolder) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BSkirmishHumanGeneratorOnCreateTrigger && node.playerId == holder.item.playerId
            }
            val uiTrigger = BSkirmishHumanGeneratorHolderOnCreateTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}