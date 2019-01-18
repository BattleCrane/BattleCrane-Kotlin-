package com.orego.battlecrane.bc.scenario.skirmish.model.race.human.unit.building.barracks.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.BOnLevelActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node.BOnLevelActionNode
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanBarracks

class BSkirmishHumanBarracksOnLevelActionTrigger private constructor(context: BGameContext, val barracks: BHumanBarracks) :
    BNode(context) {

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
            this.pushToInnerPipes(event)
            this.changeHitPointsByLevel()
            return event
        }
        return null
    }

    private fun changeHitPointsByLevel() {
        val hitPointableId = this.barracks.hitPointableId
        val currentLevel = this.barracks.currentLevel
        if (currentLevel in BHumanBarracks.FIRST_LEVEL..BHumanBarracks.MAX_LEVEL) {
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

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val barracks = this@BSkirmishHumanBarracksOnLevelActionTrigger.barracks

        override fun isUnused() = this@BSkirmishHumanBarracksOnLevelActionTrigger.isUnused()
    }

    companion object {

        fun connect(context: BGameContext, barracks: BHumanBarracks) {
            BOnLevelActionNode.connect(context) {
                BSkirmishHumanBarracksOnLevelActionTrigger(
                    context,
                    barracks
                )
            }
        }
    }
}