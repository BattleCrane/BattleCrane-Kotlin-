package com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.unit.building.factory.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.BOnLevelActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node.BOnLevelActionNode
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanFactory

class BSkirmishHumanFactoryOnLevelActionTrigger private constructor(context: BGameContext, val factory: BHumanFactory) :
    BNode(context) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnLevelActionPipe.Event
            && this.factory.levelableId == event.levelableId
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
        val hitPointableId = this.factory.hitPointableId
        val currentLevel = this.factory.currentLevel
        if (currentLevel in BHumanFactory.FIRST_LEVEL..BHumanFactory.MAX_LEVEL) {
            val newHitPoints =
                when (currentLevel) {
                    BHumanFactory.FIRST_LEVEL -> BHumanFactory.LEVEL_1_MAX_HIT_POINTS
                    BHumanFactory.SECOND_LEVEL -> BHumanFactory.LEVEL_2_MAX_HIT_POINTS
                    else -> BHumanFactory.LEVEL_3_MAX_HIT_POINTS
                }
            this.pipeline.pushEvent(
                BOnHitPointsActionPipe.Max.createOnChangedEvent(hitPointableId, newHitPoints)
            )
            this.pipeline.pushEvent(
                BOnHitPointsActionPipe.Current.createOnChangedEvent(hitPointableId, newHitPoints)
            )
        }
    }

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