package com.orego.battlecrane.bc.std.scenario.skirmish.trigger.onHitPointsAction

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.node.BOnHitPointsActionNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.BOnCreateUnitPipe
import com.orego.battlecrane.bc.api.model.player.BPlayer
import com.orego.battlecrane.bc.api.model.property.hitPointable.BHitPointable
import com.orego.battlecrane.bc.api.model.property.hitPointable.trigger.BOnHitPointsActionTrigger
import com.orego.battlecrane.bc.std.scenario.skirmish.model.location.grass.field.destroyed.trigger.BSkirmishDestroyedGrassFieldOnCreateTrigger

class BSkirmishOnHitPointsActionTrigger private constructor(context: BGameContext, hitPointable: BHitPointable) :
    BOnHitPointsActionTrigger(context, hitPointable) {

    override fun getInsteadDestroyedUnitEvent(x: Int, y: Int): BOnCreateUnitPipe.Event =
        BSkirmishDestroyedGrassFieldOnCreateTrigger.Event.create(BPlayer.NEUTRAL_PLAYER_ID, x, y)

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