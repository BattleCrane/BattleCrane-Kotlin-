package com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.trigger

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.trigger.BSkirmishEmptyGrassFieldOnCreateTrigger

class BSkirmishEmptyGrassFieldHolderOnCreateTrigger private constructor(
    private val uiGameContext: BUiGameContext
) : BNode(uiGameContext.gameContext) {

    override fun handle(event: BEvent): BEvent? {
        if (event is BSkirmishEmptyGrassFieldOnCreateTrigger.Event) {
            this.uiGameContext.uiTaskManager.addTask {
                val gameContext = this.uiGameContext.gameContext
                val destroyedField = gameContext.mapController.getUnitByPosition(gameContext, event.x, event.y)
                this.uiGameContext.uiUnitFactory.build(this.uiGameContext, destroyedField)
            }
        }
        return null
    }

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this))

    companion object {

        fun connect(uiGameContext: BUiGameContext) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BSkirmishEmptyGrassFieldOnCreateTrigger
            }
            val uiTrigger = BSkirmishEmptyGrassFieldHolderOnCreateTrigger(uiGameContext)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}