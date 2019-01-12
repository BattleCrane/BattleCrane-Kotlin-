package com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.BOnLevelActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node.BOnLevelActionNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.BHumanBarracks

@BUnitComponent
class BHumanBarracksOnLevelActionTrigger(context: BGameContext, val barracks: BHumanBarracks) :
    BNode(context) {

    companion object {

        fun connect(context: BGameContext, barracks: BHumanBarracks) {
            val pipe = BHumanBarracksOnLevelActionTrigger(context, barracks).intoPipe()
            context.pipeline.bindPipeToNode(BOnLevelActionNode.NAME, pipe)
        }
    }

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    private val unitMap = context.storage.getHeap(BUnitHeap::class.java).objectMap


    override fun handle(event: BEvent): BEvent? {
        if (event is BOnLevelActionPipe.Event
            && this.barracks.levelableId == event.levelableId
            && event.isEnable(this.context)
        ) {
            event.perform(this.context)
            this.pushEventIntoPipes(event)
            this.changeHitPointsByLevel()
            return event
        }
        return null
    }

    private fun changeHitPointsByLevel() {
        val hitPointableId = this.barracks.hitPointableId
        val currentLevel = this.barracks.currentLevel
        if (currentLevel in 1..3) {
            val newHitPoints =
                when (currentLevel) {
                    BHumanBarracks.FIRST_LEVEL -> BHumanBarracks.LEVEL_1_MAX_HIT_POINTS
                    BHumanBarracks.SECOND_LEVEL -> BHumanBarracks.LEVEL_2_MAX_HIT_POINTS
                    else -> BHumanBarracks.LEVEL_3_MAX_HIT_POINTS
                }
            this.pipeline.pushEvent(
                BOnHitPointsActionPipe.Max.createOnChangedEvent(hitPointableId, newHitPoints)
            )
            this.pipeline.pushEvent(
                BOnHitPointsActionPipe.Current.createOnChangedEvent(hitPointableId, newHitPoints)
            )
        }
    }

    override fun intoPipe() = Pipe()

    override fun isUnused() = !this.unitMap.containsKey(this.barracks.unitId)

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val barracks = this@BHumanBarracksOnLevelActionTrigger.barracks

        override fun isUnused() = this@BHumanBarracksOnLevelActionTrigger.isUnused()
    }
}