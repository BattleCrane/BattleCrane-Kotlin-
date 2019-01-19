package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.generator.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.BOnLevelActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node.BOnLevelActionNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanGenerator

class BSkirmishHumanGeneratorOnLevelActionTrigger private constructor(context: BGameContext, var generator: BHumanGenerator) :
    BNode(context) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnLevelActionPipe.Event
            && this.generator.levelableId == event.levelableId
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
        val hitPointableId = this.generator.hitPointableId
        val currentLevel = this.generator.currentLevel
        if (currentLevel in BHumanGenerator.FIRST_LEVEL..BHumanGenerator.MAX_LEVEL) {
            val newHitPoints =
                when (currentLevel) {
                    BHumanGenerator.FIRST_LEVEL -> BHumanGenerator.LEVEL_1_MAX_HIT_POINTS
                    BHumanGenerator.SECOND_LEVEL -> BHumanGenerator.LEVEL_2_MAX_HIT_POINTS
                    else -> BHumanGenerator.LEVEL_3_MAX_HIT_POINTS
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

        fun connect(context: BGameContext, generator: BHumanGenerator) {
            BOnLevelActionNode.connect(context) {
                BSkirmishHumanGeneratorOnLevelActionTrigger(context, generator)
            }
        }
    }
}