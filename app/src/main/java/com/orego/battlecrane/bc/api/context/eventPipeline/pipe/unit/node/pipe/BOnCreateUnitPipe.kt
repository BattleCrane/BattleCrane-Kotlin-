package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.node.pipe

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.BUnitPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.node.BUnitNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.node.pipe.node.BOnCreateUnitNode
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.model.unit.BUnit

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