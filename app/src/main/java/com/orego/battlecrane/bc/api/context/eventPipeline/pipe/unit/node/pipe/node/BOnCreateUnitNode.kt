package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.node.pipe.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.node.pipe.BOnCreateUnitPipe

class BOnCreateUnitNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "ON_CREATE_UNIT_NODE"

        const val EVENT = "ON_CREATE_UNIT_EVENT"
    }

    override val name =
        NAME

    private val mapManager = context.mapManager

    /**
     * Creates new unit.
     */

    override fun handle(event: BEvent) : BEvent? {
        val name = event.name!!
        val bundle = event.any!!
        return if (name == EVENT && bundle is BOnCreateUnitPipe.OnCreateUnitBundle) {
            val unit = bundle.unit
            val point = bundle.point
            if (this.mapManager.createUnit(unit, point)) {
                unit.onCreate()
                this.pipeMap.values.forEach { pipe -> pipe.push(event) }
            }
            event
        } else {
            null
        }
    }
}