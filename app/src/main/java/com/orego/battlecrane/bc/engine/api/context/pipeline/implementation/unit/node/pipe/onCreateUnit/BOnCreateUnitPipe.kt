package com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.BUnitPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.node.pipe.onCreateUnit.node.BOnCreateUnitNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe

class BOnCreateUnitPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_CREATE_UNIT_PIPE"
    }

    override val name =
        NAME

    init {
        this.placeNode(BOnCreateUnitNode(context))
    }

    open class Event(val x: Int, val y: Int) : BUnitPipe.Event()
}