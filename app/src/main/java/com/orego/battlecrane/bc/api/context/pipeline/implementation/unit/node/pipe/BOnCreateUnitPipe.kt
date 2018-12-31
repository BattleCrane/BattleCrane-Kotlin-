package com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.BUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.node.BOnCreateUnitNode
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