package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.infantry.trigger

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.heap.BUiUnitHeap
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.infantry.BUiHumanMarine
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.util.trigger.hitPointable.BOnHitPointsActionTrigger

class BSkirmishHumanMarineHolderOnHitPointsActionTrigger private constructor(
    val uiGameContext: BUiGameContext,
    var holder: BUiHumanMarine
) : BNode(uiGameContext.gameContext) {

    private val unitMap = this.context.storage.getHeap(BUiUnitHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        val marine = this.holder.item
        if (event is BOnHitPointsActionPipe.Event
            && event.hitPointableId == marine.hitPointableId
        ) {
            if (marine.currentHitPoints > 0) {
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

        val holder = this@BSkirmishHumanMarineHolderOnHitPointsActionTrigger.holder

        override fun isFinished() = this@BSkirmishHumanMarineHolderOnHitPointsActionTrigger.isFinished()
    }

    companion object {

        fun connect(uiGameContext: BUiGameContext, holder: BUiHumanMarine) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnHitPointsActionTrigger && node.hitPointable == holder.item
            }
            val uiTrigger = BSkirmishHumanMarineHolderOnHitPointsActionTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}