package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.barracks.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node.BOnLevelActionNode
import com.orego.battlecrane.bc.engine.api.util.pipe.BParentPipe
import com.orego.battlecrane.bc.engine.api.util.trigger.levelable.BOnLevelActionTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanBarracks

class BSkirmishHumanBarracksOnLevelActionTrigger private constructor(
    context: BGameContext,
    override val levelable: BHumanBarracks
) : BOnLevelActionTrigger(context, levelable) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    override fun onLevelChanged() {
        val hitPointableId = this.levelable.hitPointableId
        val currentLevel = this.levelable.currentLevel
        if (currentLevel in BHumanBarracks.FIRST_LEVEL..BHumanBarracks.MAX_LEVEL) {
            val newHitPoints =
                when (currentLevel) {
                    BHumanBarracks.FIRST_LEVEL -> BHumanBarracks.LEVEL_1_MAX_HIT_POINTS
                    BHumanBarracks.SECOND_LEVEL -> BHumanBarracks.LEVEL_2_MAX_HIT_POINTS
                    else -> BHumanBarracks.LEVEL_3_MAX_HIT_POINTS
                }
            println("ON LVL CHANGED")
            this.pipeline.pushEvent(
                BOnHitPointsActionPipe.Max.OnChangedEvent(hitPointableId, newHitPoints)
            )
            this.pipeline.pushEvent(
                BOnHitPointsActionPipe.Current.OnChangedEvent(hitPointableId, newHitPoints)
            )
        }
    }

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BParentPipe(this)

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