package com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.trigger

@BUnitComponent
    class BHumanBarracksOnHitPointsActionTrigger(context: BGameContext, unitId: Long) : BNode(context) {

        /**
         * Context.
         */

        private val pipeline = context.pipeline

        /**
         * Unit.
         */

        private val barracks by lazy {
            context.storage.getHeap(BUnitHeap::class.java)[unitId] as BHumanBarracks
        }

        override fun handle(event: BEvent): BEvent? {
            if (event is BOnHitPointsActionPipe.Event
                && event.hitPointableId == this.barracks.hitPointableId
                && event.isEnable(this.context)
            ) {
                event.perform(this.context)
                this.pushEventIntoPipes(event)
                if (this.barracks.currentHitPoints <= 0) {
                    this.pipeline.pushEvent(BOnDestroyUnitPipe.createEvent(this.barracks.unitId))
                }
                return event
            }
            return null
        }
    }