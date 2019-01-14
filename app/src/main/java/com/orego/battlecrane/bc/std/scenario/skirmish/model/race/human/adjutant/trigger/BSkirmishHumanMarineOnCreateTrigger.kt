package com.orego.battlecrane.bc.std.scenario.skirmish.model.race.human.adjutant.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.BOnCreateUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.node.BOnCreateUnitNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.adjutant.BAdjutantComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.std.race.human.unit.infantry.implementation.marine.BHumanMarine


@BAdjutantComponent
class BSkirmishHumanMarineOnCreateTrigger private constructor(context: BGameContext, private val playerId: Long) :
    BNode(context) {

    companion object {

        fun connect(context: BGameContext, playerId: Long) {
            val pipe = BSkirmishHumanMarineOnCreateTrigger(
                context,
                playerId
            ).intoPipe()
            context.pipeline.bindPipeToNode(BOnCreateUnitNode.NAME, pipe)
        }

        fun createEvent(playerId: Long, x: Int, y: Int) =
            Event(
                playerId,
                x,
                y
            )
    }

    override fun handle(event: BEvent): BEvent? {
        if (event is Event
            && event.playerId == this.playerId
            && event.perform(this.context)
        ) {
            return this.pushEventIntoPipes(event)
        }
        return null
    }

    override fun intoPipe() = Pipe()

    /**
     * Event.
     */

    open class Event(val playerId: Long, x: Int, y: Int) :
        BOnCreateUnitPipe.Event(x, y) {

        fun perform(context: BGameContext): Boolean {
            val marine = BHumanMarine(
                context,
                this.playerId,
                this.x,
                this.y
            )
            val isSuccessful = context.mapController.placeUnitOnMap(marine)
            if (isSuccessful) {
                context.storage.addObject(marine)
            }
            return isSuccessful
        }
    }

    /**
     * Pipe.
     */

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val playerId = this@BSkirmishHumanMarineOnCreateTrigger.playerId
    }
}