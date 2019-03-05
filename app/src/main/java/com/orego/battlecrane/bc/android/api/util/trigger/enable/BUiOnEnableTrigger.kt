package com.orego.battlecrane.bc.android.api.util.trigger.enable

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.heap.BUiUnitHeap
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.util.pipe.BParentPipe

abstract class BUiOnEnableTrigger(protected val uiGameContext: BUiGameContext, protected open val uiUnit: BUiUnit) :
    BNode(uiGameContext.gameContext) {

    private val unitMap = this.context.storage.getHeap(BUiUnitHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        if (this.mathchesEvent(event)) {
            val task: suspend () -> Unit = this.getUiTask(event)
            this.uiGameContext.uiTaskManager.addTask(task)
        }
        return null
    }

    /**
     * Tries to switch and update view.
     */

    private fun getUiTask(event: BEvent) : suspend () -> Unit  = {
        if (event.isEnable()) {
            this.uiUnit.activate(this.uiGameContext)
        } else {
            this.uiUnit.release(this.uiGameContext)
        }
    }

    /**
     * Checks event.
     */

    protected abstract fun mathchesEvent(event: BEvent?): Boolean

    protected abstract fun BEvent.isEnable(): Boolean

    /**
     * Pipeline.
     */

    override fun intoPipe() = Pipe()

    override fun isFinished() = !this.unitMap.containsKey(this.uiUnit.uiUnitId)

    /**
     * Pipe.
     */

    inner class Pipe : BParentPipe(this)
}