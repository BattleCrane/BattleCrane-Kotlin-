package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.wall.trigger

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.heap.BUnitHolderHeap
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BHumanWallHolder
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.util.trigger.hitPointable.BOnHitPointsActionTrigger
import com.orego.battlecrane.ui.util.setImageByAssets

class BSkirmishHumanWallHolderOnHitPointsActionTrigger private constructor(
    val uiGameContext: BUiGameContext,
    var holder: BHumanWallHolder
) : BNode(uiGameContext.gameContext) {

    private val unitMap = this.context.storage.getHeap(BUnitHolderHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        val wall = this.holder.item
        if (event is BOnHitPointsActionPipe.Event
            && event.hitPointableId == wall.hitPointableId
        ) {
            if (wall.currentHitPoints > 0) {
                this.uiGameContext.uiTaskManager.addTask {
                    val image = this.holder.unitView
                    val applicationContext = this.uiGameContext.uiProvider.applicationContext
                    image.setImageByAssets(applicationContext, this.holder.getItemPath())
                }
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

        val holder = this@BSkirmishHumanWallHolderOnHitPointsActionTrigger.holder

        override fun isUnused() = this@BSkirmishHumanWallHolderOnHitPointsActionTrigger.isFinished()
    }

    companion object {

        fun connect(uiGameContext: BUiGameContext, holder: BHumanWallHolder) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnHitPointsActionTrigger && node.hitPointable == holder.item
            }
            val uiTrigger = BSkirmishHumanWallHolderOnHitPointsActionTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}