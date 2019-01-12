package com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.unit.node.pipe.onDestroyUnit.BOnDestroyUnitPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.unit.BUnitComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.BHumanBarracks

@BUnitComponent
    class BHumanBarracksOnDestroyTrigger(context: BGameContext, unitId: Long) : BNode(context) {

        /**
         * Context.
         */

        private val storage = context.storage

        /**
         * Unit.
         */

        private val barracks by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanBarracks
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnDestroyUnitPipe.Event && event.unitId == this.barracks.unitId) {
                this.pushEventIntoPipes(event)
                this.unbindPipes()
                this.storage.removeObject(event.unitId, BUnitHeap::class.java)
                return event
            }
            return null
        }

        private fun unbindPipes() {
            this.barracks.turnConnection.disconnect(this.context)
            this.barracks.produceEnableConnection.disconnect(this.context)
            this.barracks.produceActionConnection.disconnect(this.context)
            this.barracks.destroyConnection.disconnect(this.context)
            this.barracks.levelActionConnection.disconnect(this.context)
            this.barracks.hitPointsActionConnection.disconnect(this.context)
        }
    }
