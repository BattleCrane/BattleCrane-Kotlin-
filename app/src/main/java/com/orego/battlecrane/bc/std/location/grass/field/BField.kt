package com.orego.battlecrane.bc.std.location.grass.field

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.node.BOnDestroyUnitNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipeConnection
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit

abstract class BField(context: BGameContext, playerid: Long, x: Int, y: Int) :
    BUnit(context, playerid, x, y) {

    companion object {

        const val HEIGHT = 1

        const val WIDTH = 1
    }

    final override val height = HEIGHT

    final override val width = WIDTH

    val destroyConnection = BPipeConnection.createByNode(
        context, BOnDestroyUnitNode.NAME, OnDestroyNode(context, this.unitId)
    )

    @BUnitComponent
    class OnDestroyNode(context: BGameContext, unitId: Long) : BNode(context) {

        /**
         * Context.
         */

        private val storage = context.storage

        /**
         * Unit.
         */

        private val field by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BField
        }

        override fun handle(event: BEvent): BEvent? {
            val unitId = this.field.unitId
            if (event is BOnDestroyUnitPipe.Event && event.unitId == unitId) {
                this.unbindPipes()
                this.storage.removeObject(unitId, BUnitHeap::class.java)
                return this.pushEventIntoPipes(event)
            }
            return null
        }

        private fun unbindPipes() {
            this.field.destroyConnection.disconnect(this.context)
        }
    }
}