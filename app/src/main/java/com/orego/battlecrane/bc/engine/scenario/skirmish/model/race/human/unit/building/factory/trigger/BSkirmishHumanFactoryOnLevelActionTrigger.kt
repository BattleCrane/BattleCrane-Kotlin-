package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.factory.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node.BOnLevelActionNode
import com.orego.battlecrane.bc.engine.api.util.pipe.BParentPipe
import com.orego.battlecrane.bc.engine.api.util.trigger.levelable.BOnLevelActionTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanFactory

class BSkirmishHumanFactoryOnLevelActionTrigger private constructor(
    context: BGameContext,
    override val levelable: BHumanFactory
) : BOnLevelActionTrigger(context, levelable) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    override fun onLevelChanged() {
        val hitPointableId = this.levelable.hitPointableId
        val currentLevel = this.levelable.currentLevel
        if (currentLevel in BHumanFactory.FIRST_LEVEL..BHumanFactory.MAX_LEVEL) {
            val newHitPoints =
                when (currentLevel) {
                    BHumanFactory.FIRST_LEVEL -> BHumanFactory.LEVEL_1_MAX_HIT_POINTS
                    BHumanFactory.SECOND_LEVEL -> BHumanFactory.LEVEL_2_MAX_HIT_POINTS
                    else -> BHumanFactory.LEVEL_3_MAX_HIT_POINTS
                }
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

        fun connect(context: BGameContext, factory: BHumanFactory) {
            BOnLevelActionNode.connect(context) {
                BSkirmishHumanFactoryOnLevelActionTrigger(
                    context,
                    factory
                )
            }
        }
    }
}