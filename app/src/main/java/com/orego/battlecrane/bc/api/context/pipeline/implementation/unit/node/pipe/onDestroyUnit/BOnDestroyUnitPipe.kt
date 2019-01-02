package com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.BUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.node.BOnDestroyUnitNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe

@BContextComponent
class BOnDestroyUnitPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_DESTROY_UNIT_PIPE"

        fun createEvent(unitId: Long) = Event(unitId)
    }

    override val name = NAME

    init {
        this.placeNode(BOnDestroyUnitNode(context))
    }

    open class Event(val unitId : Long) : BUnitPipe.Event()
}