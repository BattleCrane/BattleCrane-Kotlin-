package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.unit.node.pipe.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.unit.node.pipe.BOnCreateUnitPipe

class BOnCreateUnitNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_CREATE_UNIT_NODE"
    }

    override val name = NAME

    private val mapManager = context.mapManager

    /**
     * Creates new unit.
     */

    override fun handle(event: BEvent) : BEvent? {
        val name = event.name!!
        val bundle = event.bundle!!
        return if (name == BOnCreateUnitPipe.EVENT && bundle is BOnCreateUnitPipe.OnCreateUnitBundle) {
            val unit = bundle.unit
            val point = bundle.point
            if (this.mapManager.createUnit(unit, point)) {
                unit.onCreate()
                this.pipeMap.values.forEach { pipe -> pipe.push(event) }
            };
            event
        } else {
            null
        }
    }
}