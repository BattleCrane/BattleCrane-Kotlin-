package com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.trigger

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.heap.BUiUnitHeap
import com.orego.battlecrane.bc.android.standardImpl.location.grass.unit.field.empty.BUiEmptyGrassField
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onOwnerChanged.BOnOwnerChangedUnitPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.util.pipe.BParentPipe
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnOwnerChangedUnitTrigger

class BUiSkirmishEmptyGrassFieldOnOwnerChangedTrigger private constructor(
    val uiGameContext: BUiGameContext,
    val holder: BUiEmptyGrassField
) : BNode(uiGameContext.gameContext) {

    private val unitMap = this.context.storage.getHeap(BUiUnitHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnOwnerChangedUnitPipe.Event && this.holder.unit.unitId == event.unitId) {
            this.uiGameContext.uiTaskManager.addTask {
                this.holder.onUpdateView(this.uiGameContext)
            }
        }
        return null
    }

    override fun intoPipe() = Pipe()

    override fun isFinished() = !this.unitMap.containsKey(this.holder.uiUnitId)

    /**
     * Pipe.
     */

    inner class Pipe : BParentPipe(this)

    companion object {

        fun connect(uiGameContext: BUiGameContext, holder: BUiEmptyGrassField) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnOwnerChangedUnitTrigger && node.unit == holder.unit
            }
            val uiTrigger = BUiSkirmishEmptyGrassFieldOnOwnerChangedTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}