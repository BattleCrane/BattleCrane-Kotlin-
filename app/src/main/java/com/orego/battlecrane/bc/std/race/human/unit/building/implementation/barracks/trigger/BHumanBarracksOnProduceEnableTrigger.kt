package com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.node.BOnProduceEnableNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.BHumanBarracks

@BUnitComponent
class BHumanBarracksOnProduceEnableTrigger(context: BGameContext, val barracks: BHumanBarracks) :
    BNode(context) {

    companion object {

        fun connect(context: BGameContext, barracks: BHumanBarracks) {
            val pipe = BHumanBarracksOnProduceEnableTrigger(context, barracks).intoPipe()
            context.pipeline.bindPipeToNode(BOnProduceEnableNode.NAME, pipe)
        }
    }

    private val unitMap = context.storage.getHeap(BUnitHeap::class.java).objectMap

    override fun handle(event: BEvent): BEvent? {
        if (event is BOnProduceEnablePipe.Event
            && this.barracks.producableId == event.producableId
            && event.isEnable(this.context)
        ) {
            event.perform(this.context)
            this.pushEventIntoPipes(event)
            return event
        }
        return null
    }

    override fun intoPipe() = Pipe()

    override fun isUnused() = !this.unitMap.containsKey(this.barracks.unitId)

    inner class Pipe : BPipe(this.context, mutableListOf(this)) {

        val barracks = this@BHumanBarracksOnProduceEnableTrigger.barracks

        override fun isUnused() = this@BHumanBarracksOnProduceEnableTrigger.isUnused()
    }
}