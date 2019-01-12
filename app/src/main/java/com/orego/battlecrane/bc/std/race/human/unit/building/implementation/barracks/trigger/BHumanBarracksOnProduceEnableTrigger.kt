package com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.trigger

@BUnitComponent
    class BHumanBarracksOnProduceEnableTrigger(context: BGameContext, unitId: Long) : BNode(context) {

        private val barracks by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanBarracks
        }

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
    }
