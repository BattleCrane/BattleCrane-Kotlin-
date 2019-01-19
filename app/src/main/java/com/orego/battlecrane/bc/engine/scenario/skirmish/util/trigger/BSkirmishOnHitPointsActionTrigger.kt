package com.orego.battlecrane.bc.engine.scenario.skirmish.util.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.node.BOnHitPointsActionNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.engine.api.model.player.BPlayer
import com.orego.battlecrane.bc.engine.api.model.property.BHitPointable
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.api.util.trigger.hitPointable.BOnHitPointsActionTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.trigger.BSkirmishDestroyedGrassFieldOnCreateTrigger

class BSkirmishOnHitPointsActionTrigger private constructor(context: BGameContext, hitPointable: BHitPointable) :
    BOnHitPointsActionTrigger(context, hitPointable) {

    private val pipeline = context.pipeline

    override fun onHitPointsLost() {
        if (this.hitPointable is BUnit) {
            this.pipeline.pushEvent(
                BOnDestroyUnitPipe.Event(this.hitPointable.unitId)
            )
            val hitPointableX = this.hitPointable.x
            val hitPointableY = this.hitPointable.y
            for (x in hitPointableX until this.hitPointable.width) {
                for (y in hitPointableY until this.hitPointable.height) {
                    this.pipeline.pushEvent(
                        BSkirmishDestroyedGrassFieldOnCreateTrigger.Event(
                            BPlayer.NEUTRAL_PLAYER_ID, x, y
                        )
                    )
                }
            }
        }
    }

    companion object {

        fun connect(context: BGameContext, hitPointable: BHitPointable) {
            BOnHitPointsActionNode.connect(context) {
                BSkirmishOnHitPointsActionTrigger(
                    context,
                    hitPointable
                )
            }
        }
    }
}