package com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.node.pipe

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.BUnitPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.node.BUnitNode
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.node.pipe.node.BOnCreateUnitNode
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.model.unit.BUnit

class BOnCreateUnitPipe(context: BGameContext) : BEventPipeline.Pipe(context) {

    companion object {

        const val NAME = "${BUnitNode.NAME}/ON_CREATE_UNIT_PIPE"
    }

    override val name = NAME

    override val nodes = mutableListOf<Node>(BOnCreateUnitNode(context))

    open class OnCreateUnitBundle(unit: BUnit, val point : BPoint) : BUnitPipe.UnitBundle(unit)
}