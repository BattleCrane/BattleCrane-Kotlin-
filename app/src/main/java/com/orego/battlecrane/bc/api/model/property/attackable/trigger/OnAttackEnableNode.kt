package com.orego.battlecrane.bc.api.model.property.attackable.trigger

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.BOnAttackEnablePipe
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BUnitHeap
import com.orego.battlecrane.bc.std.race.human.unit.infantry.implementation.marine.BHumanMarine

class OnAttackEnableNode(context: BGameContext, unitId: Long) : BNode(context) {

        private val marine by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanMarine
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnAttackEnablePipe.Event
                && this.marine.attackableId == event.attackableId
                && event.isEnable(this.context)
            ) {
                event.perform(this.context)
                return this.pushToInnerPipes(event)
            }
            return null
        }
    }
