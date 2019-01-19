package com.orego.battlecrane.bc.engine.scenario.skirmish.model.race.human.unit.building.turret.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node.BOnLevelActionNode
import com.orego.battlecrane.bc.engine.api.util.trigger.levelable.BOnLevelActionTrigger
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.BHumanTurret

class BSkirmishHumanTurretOnLevelActionTrigger private constructor(
    context: BGameContext,
    override var levelable: BHumanTurret
) : BOnLevelActionTrigger(context, levelable) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    override fun onLevelChanged() {
        val hitPointableId = this.levelable.hitPointableId
        val currentLevel = this.levelable.currentLevel
        if (currentLevel in BHumanTurret.FIRST_LEVEL..BHumanTurret.MAX_LEVEL) {
            val newHitPoints =
                when (currentLevel) {
                    BHumanTurret.FIRST_LEVEL -> BHumanTurret.LEVEL_1_MAX_HIT_POINTS
                    else -> BHumanTurret.LEVEL_2_MAX_HIT_POINTS
                }
            this.pipeline.pushEvent(
                BOnHitPointsActionPipe.Max.OnChangedEvent(hitPointableId, newHitPoints)
            )
            this.pipeline.pushEvent(
                BOnHitPointsActionPipe.Current.OnChangedEvent(hitPointableId, newHitPoints)
            )
        }
    }

    companion object {

        fun connect(context: BGameContext, turret: BHumanTurret) {
            BOnLevelActionNode.connect(context) {
                BSkirmishHumanTurretOnLevelActionTrigger(context, turret)
            }
        }
    }
}
