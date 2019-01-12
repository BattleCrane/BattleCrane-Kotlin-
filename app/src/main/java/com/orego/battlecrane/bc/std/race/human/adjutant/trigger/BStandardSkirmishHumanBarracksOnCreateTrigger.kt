package com.orego.battlecrane.bc.std.race.human.adjutant.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.BOnCreateUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.node.BOnCreateUnitNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.adjutant.BAdjutantComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.BHumanBarracks

@BAdjutantComponent
class BStandardSkirmishHumanBarracksOnCreateTrigger(context: BGameContext, private val playerId: Long) :
    BNode(context) {

    companion object {

        fun connect(context: BGameContext, playerId: Long) {
            val pipe = BStandardSkirmishHumanBarracksOnCreateTrigger(context, playerId).wrapInPipe()
            context.pipeline.bindPipeToNode(BOnCreateUnitNode.NAME, pipe)
        }

        fun createEvent(playerId: Long, x: Int, y: Int) =
            Event(playerId, x, y)
    }

    override fun handle(event: BEvent): BEvent? {
        if (event is Event && event.playerId == this.playerId && event.perform(this.context)) {
            return this.pushEventIntoPipes(event)
        }
        return null
    }

    override fun wrapInPipe() = Pipe()

    /**
     * Event.
     */

    class Event(val playerId: Long, x: Int, y: Int) : BOnCreateUnitPipe.Event(x, y) {

        fun perform(context: BGameContext): Boolean {
            val controller = context.mapController
            val startX = this.x
            val startY = this.y
            val barracks =
                BHumanBarracks(
                    context,
                    this.playerId,
                    startX,
                    startY
                )
            val isSuccessful = controller.placeUnitOnMap(barracks)
            if (isSuccessful) {
                val pipeline = context.pipeline
                for (x in startX until startX + BHumanBarracks.WIDTH) {
                    for (y in startY until startY + BHumanBarracks.HEIGHT) {
                        val unitId = controller.getUnitIdByPosition(x, y)
                        pipeline.pushEvent(BOnDestroyUnitPipe.createEvent(unitId))
                    }
                }
                context.storage.addObject(barracks)
            }
            return isSuccessful
        }
    }

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val playerId = this@BStandardSkirmishHumanBarracksOnCreateTrigger.playerId
    }
}