package com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.trigger

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.util.pipe.BParentPipe
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.trigger.BSkirmishDestroyedGrassFieldOnCreateTrigger

class BUiSkirmishDestroyedGrassFieldOnCreateTrigger private constructor(
    private val uiGameContext: BUiGameContext
) : BNode(uiGameContext.gameContext) {

    override fun handle(event: BEvent): BEvent? {
        if (event is BSkirmishDestroyedGrassFieldOnCreateTrigger.Event) {
            this.uiGameContext.uiTaskManager.addTask {
                val gameContext = this.uiGameContext.gameContext
                val destroyedField = gameContext.mapController.getUnitByPosition(event.x, event.y)
                this.uiGameContext.uiUnitFactory.build(this.uiGameContext, destroyedField)
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

        fun connect(uiGameContext: BUiGameContext) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BSkirmishDestroyedGrassFieldOnCreateTrigger
            }
            val uiTrigger = BUiSkirmishDestroyedGrassFieldOnCreateTrigger(uiGameContext)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}