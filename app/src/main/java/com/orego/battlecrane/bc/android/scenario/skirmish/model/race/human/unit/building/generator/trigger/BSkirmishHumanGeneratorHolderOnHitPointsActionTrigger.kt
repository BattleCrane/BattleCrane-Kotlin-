package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.generator.trigger

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.context.heap.BUiUnitHeap
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BUiHumanGenerator
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.util.trigger.hitPointable.BOnHitPointsActionTrigger

class BSkirmishHumanGeneratorHolderOnHitPointsActionTrigger private constructor(
    val uiGameContext: BUiGameContext,
    var holder: BUiHumanGenerator
) : BNode(uiGameContext.gameContext) {

    private val unitMap = this.context.storage.getHeap(BUiUnitHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        val generator = this.holder.item
        if (event is BOnHitPointsActionPipe.Event
            && event.hitPointableId == generator.hitPointableId
        ) {
            if (generator.currentHitPoints > 0) {
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

        val holder = this@BSkirmishHumanGeneratorHolderOnHitPointsActionTrigger.holder

        override fun isFinished() = this@BSkirmishHumanGeneratorHolderOnHitPointsActionTrigger.isFinished()
    }

    companion object {

        fun connect(uiGameContext: BUiGameContext, holder: BUiHumanGenerator) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnHitPointsActionTrigger && node.hitPointable == holder.item
            }
            val uiTrigger = BSkirmishHumanGeneratorHolderOnHitPointsActionTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}