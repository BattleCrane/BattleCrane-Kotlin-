package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.generator.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node.BOnLevelActionNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.util.trigger.levelable.BOnLevelActionTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanGenerator

class BSkirmishHumanGeneratorOnLevelActionTrigger private constructor(
    context: BGameContext,
    override var levelable: BHumanGenerator
) : BOnLevelActionTrigger(context, levelable) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    override fun onLevelChanged() {
        val hitPointableId = this.levelable.hitPointableId
        val currentLevel = this.levelable.currentLevel
        if (currentLevel in BHumanGenerator.FIRST_LEVEL..BHumanGenerator.MAX_LEVEL) {
            val newHitPoints =
                when (currentLevel) {
                    BHumanGenerator.FIRST_LEVEL -> BHumanGenerator.LEVEL_1_MAX_HIT_POINTS
                    BHumanGenerator.SECOND_LEVEL -> BHumanGenerator.LEVEL_2_MAX_HIT_POINTS
                    else -> BHumanGenerator.LEVEL_3_MAX_HIT_POINTS
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

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        var levelable = this@BSkirmishHumanGeneratorOnLevelActionTrigger.levelable

        override fun isFinished() = this@BSkirmishHumanGeneratorOnLevelActionTrigger.isFinished()
    }

    companion object {

        fun connect(context: BGameContext, generator: BHumanGenerator) {
            BOnLevelActionNode.connect(context) {
                BSkirmishHumanGeneratorOnLevelActionTrigger(context, generator)
            }
        }
    }
}