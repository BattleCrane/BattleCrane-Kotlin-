package com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.trigger

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.model.player.BPlayer
import com.orego.battlecrane.bc.engine.api.util.trigger.unit.BOnCreateUnitTrigger
import com.orego.battlecrane.bc.engine.scenario.skirmish.model.location.grass.field.BSkirmishDestroyedGrassFieldBuilder
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.BGrassField

class BSkirmishDestroyedGrassFieldOnCreateTrigger(context: BGameContext) :
    BOnCreateUnitTrigger(context, BPlayer.NEUTRAL_PLAYER_ID) {

    override fun handle(event: BEvent): BEvent? {
        if (event is Event) {
            return super.handle(event)
        }
        return null
    }

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    inner class Pipe : BOnCreateUnitTrigger.Pipe()

    /**
     * Static.
     */

    companion object {

        fun connect(context: BGameContext) {
            BOnCreateUnitTrigger.connect(context) {
                BSkirmishDestroyedGrassFieldOnCreateTrigger(
                    context
                )
            }
        }
    }


    /**
     * Event.
     */

    class Event (playerId: Long, x: Int, y: Int) : BOnCreateUnitTrigger.Event(playerId, x, y) {

        override val height = BGrassField.HEIGHT

        override val width = BGrassField.WIDTH

        override fun createUnit(context: BGameContext) =
            BSkirmishDestroyedGrassFieldBuilder().build(
                context,
                this.playerId,
                this.x,
                this.y
            )
    }
}