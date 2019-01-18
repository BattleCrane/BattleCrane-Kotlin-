package com.orego.battlecrane.bc.api.model.unit.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.BOnCreateUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.node.BOnCreateUnitNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.model.unit.BUnit

open class BOnCreateUnitTrigger protected constructor(context: BGameContext, var playerId: Long) :
    BNode(context) {

    override fun handle(event: BEvent): BEvent? {
        if (event is Event && event.playerId == this.playerId && event.perform(this.context)) {
            return this.pushToInnerPipes(event)
        }
        return null
    }

    override fun intoPipe() = Pipe()

    /**
     * Pipe.
     */

    open inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val playerId = this@BOnCreateUnitTrigger.playerId
    }

    /**
     * Event.
     */

    abstract class Event protected constructor(val playerId: Long, x: Int, y: Int) : BOnCreateUnitPipe.Event(x, y) {

        protected abstract val width : Int

        protected abstract val height : Int

        open fun perform(context: BGameContext): Boolean {
            val controller = context.mapController
            val pipeline = context.pipeline
            //Destroy previous units:
            for (x in this.x until this.x + this.width) {
                for (y in this.y until this.y + this.height) {
                    val unitId = controller.getUnitIdByPosition(x, y)
                    pipeline.pushEvent(BOnDestroyUnitPipe.createEvent(unitId))
                }
            }
            //Create new unit:
            val unit = this.create(context)
            controller.placeUnitOnMap(unit)
            context.storage.addObject(unit)
            return true
        }

        protected abstract fun create(context: BGameContext): BUnit
    }

    companion object {

        fun connect(context: BGameContext, create: () -> BOnCreateUnitTrigger) {
            context.pipeline.bindPipeToNode(BOnCreateUnitNode.NAME, create().intoPipe())
        }
    }
}