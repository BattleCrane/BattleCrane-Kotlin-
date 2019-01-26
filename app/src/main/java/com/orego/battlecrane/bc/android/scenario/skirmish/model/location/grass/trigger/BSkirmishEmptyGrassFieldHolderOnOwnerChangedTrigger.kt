package com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.trigger

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.heap.BUiUnitHeap
import com.orego.battlecrane.bc.android.standardImpl.location.grass.field.empty.BEmptyGrassFieldHolder
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onOwnerChanged.BOnOwnerChangedUnitPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnOwnerChangedUnitTrigger
import com.orego.battlecrane.ui.util.setImageByAssets

class BSkirmishEmptyGrassFieldHolderOnOwnerChangedTrigger private constructor(
    val uiGameContext: BUiGameContext,
    val holder: BEmptyGrassFieldHolder
) : BNode(uiGameContext.gameContext) {

    private val unitMap = this.context.storage.getHeap(BUiUnitHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnOwnerChangedUnitPipe.Event && this.holder.item.unitId == event.unitId) {
            this.uiGameContext.uiTaskManager.addTask {
                val image = this.holder.unitView
                val applicationContext = this.uiGameContext.uiProvider.applicationContext
                image.setImageByAssets(applicationContext, this.holder.getItemPath())
            }
        }
        return null
    }

    override fun intoPipe() = Pipe()

    override fun isFinished() = !this.unitMap.containsKey(this.holder.uiUnitId)

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val holder = this@BSkirmishEmptyGrassFieldHolderOnOwnerChangedTrigger.holder

        override fun isFinished() = this@BSkirmishEmptyGrassFieldHolderOnOwnerChangedTrigger.isFinished()
    }

    companion object {

        fun connect(uiGameContext: BUiGameContext, holder: BEmptyGrassFieldHolder) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnOwnerChangedUnitTrigger && node.unit == holder.item
            }
            val uiTrigger = BSkirmishEmptyGrassFieldHolderOnOwnerChangedTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}