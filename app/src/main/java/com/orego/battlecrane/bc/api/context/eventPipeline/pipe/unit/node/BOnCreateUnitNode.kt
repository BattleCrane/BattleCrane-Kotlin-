package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.model.unit.BUnit

class BOnCreateUnitNode(context: BGameContext) : BEventPipeline.Pipe.Node(context) {

    companion object {

        const val NAME = "ON_CREATE_UNIT_NODE"

        const val EVENT = "ON_CREATE_UNIT_EVENT"
    }

    override val name = NAME

    private val mapManager = context.mapManager

    /**
     * Creates new unit.
     */

    override fun handle(event: BEvent) {
        val name = event.name!!
        val bundle = event.any!!
        if (name == EVENT && bundle is Bundle) {
            val unit = bundle.unit
            val point = bundle.point
            if (this.mapManager.createUnit(unit, point)) {
                unit.onCreate()
                this.pipeMap.values.forEach { pipe -> pipe.push(event) }
            }
        }
    }

    data class Bundle(var unit: BUnit, var point: BPoint)
}