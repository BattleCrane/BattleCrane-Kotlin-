package com.orego.battlecrane.bc.scenario.skirmish.model.race.human.unit.building.turret.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.BOnHitPointsActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.BOnLevelActionPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.levelable.node.pipe.onLevelAction.node.BOnLevelActionNode
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanTurret

class BSkirmishHumanTurretOnLevelActionTrigger private constructor(context: BGameContext, var turret: BHumanTurret) :
    BNode(context) {

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnLevelActionPipe.Event
            && this.turret.levelableId == event.levelableId
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
        val hitPointableId = this.turret.hitPointableId
        val currentLevel = this.turret.currentLevel
        if (currentLevel in BHumanTurret.FIRST_LEVEL..BHumanTurret.MAX_LEVEL) {
            val newHitPoints =
                when (currentLevel) {
                    BHumanTurret.FIRST_LEVEL -> BHumanTurret.LEVEL_1_MAX_HIT_POINTS
                    else -> BHumanTurret.LEVEL_2_MAX_HIT_POINTS
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

        fun connect(context: BGameContext, turret: BHumanTurret) {
            BOnLevelActionNode.connect(context) {
                BSkirmishHumanTurretOnLevelActionTrigger(context, turret)
            }
        }
    }
}
