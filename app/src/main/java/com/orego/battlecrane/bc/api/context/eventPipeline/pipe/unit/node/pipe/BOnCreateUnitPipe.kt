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

        const val NAME = "${BUnitNode.NAME}/ON_CREATE_UNIT_PIPE"

        const val EVENT = "ON_CREATE_UNIT_EVENT"

        fun createEvent(unit: BUnit, point: BPoint) : BEvent = BEvent(EVENT, OnCreateUnitBundle(unit, point))
    }

    override val name = NAME

    override val nodes = mutableListOf<BNode>(BOnCreateUnitNode(context))

    open class OnCreateUnitBundle(unit: BUnit, val point : BPoint) : BUnitPipe.UnitBundle(unit)
}