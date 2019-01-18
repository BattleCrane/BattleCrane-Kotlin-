package com.orego.battlecrane.bc.scenario.skirmish.model.location.grass.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.model.player.BPlayer
import com.orego.battlecrane.bc.api.model.unit.trigger.BOnCreateUnitTrigger
import com.orego.battlecrane.bc.scenario.skirmish.model.location.grass.field.destroyed.BSkirmishDestroyedGrassFieldBuilder
import com.orego.battlecrane.bc.std.location.grass.field.BGrassField

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

    class Event private constructor(playerId: Long, x: Int, y: Int) : BOnCreateUnitTrigger.Event(playerId, x, y) {

        override val height = BGrassField.HEIGHT

        override val width = BGrassField.WIDTH

        override fun create(context: BGameContext) =
            BSkirmishDestroyedGrassFieldBuilder().build(
                context,
                this.playerId,
                this.x,
                this.y
            )

        companion object {

            fun create(playerId: Long, x: Int, y: Int) =
                Event(
                    playerId,
                    x,
                    y
                )
        }
    }
}