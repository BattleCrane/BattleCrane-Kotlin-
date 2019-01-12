package com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.trigger

@BUnitComponent
    class BHumanBarracksOnLevelActionTrigger(context: BGameContext, unitId: Long) : BNode(context) {

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
            if (event is BOnLevelActionPipe.Event
                && this.barracks.levelableId == event.levelableId
                && event.isEnable(this.context)
            ) {
                event.perform(this.context)
                this.pushEventIntoPipes(event)
                this.changeHitPointsByLevel()
                return event
            }
            return null
        }

        private fun changeHitPointsByLevel() {
            val hitPointableId = this.barracks.hitPointableId
            val currentLevel = this.barracks.currentLevel
            if (currentLevel in 1..3) {
                val newHitPoints =
                    when (currentLevel) {
                        FIRST_LEVEL -> LEVEL_1_MAX_HIT_POINTS
                        SECOND_LEVEL -> LEVEL_2_MAX_HIT_POINTS
                        else -> LEVEL_3_MAX_HIT_POINTS
                    }
                this.pipeline.pushEvent(
                    BOnHitPointsActionPipe.Max.createOnChangedEvent(hitPointableId, newHitPoints)
                )
                this.pipeline.pushEvent(
                    BOnHitPointsActionPipe.Current.createOnChangedEvent(hitPointableId, newHitPoints)
                )
            }
        }
    }
