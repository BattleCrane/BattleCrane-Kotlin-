package com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.unit.node.pipe

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.unit.BUnitPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipeImlementation.unit.node.pipe.node.BOnCreateUnitNode
import com.orego.battlecrane.bc.api.context.controller.map.point.BPoint

class BOnCreateUnitPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_CREATE_UNIT_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BOnCreateUnitNode(context))
    }

    open class CreateUnitEvent(val position : BPoint) : BUnitPipe.UnitEvent()
}