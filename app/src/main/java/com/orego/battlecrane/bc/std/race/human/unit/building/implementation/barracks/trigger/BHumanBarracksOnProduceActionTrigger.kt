package com.orego.battlecrane.bc.std.race.human.unit.building.implementation.barracks.trigger

@BUnitComponent
    class BHumanBarracksOnProduceActionTrigger(unitId: Long, context: BGameContext) : BNode(context) {

        companion object {

            fun createEvent(barracksUnitId: Long, x: Int, y: Int) =
                Event(
                    barracksUnitId,
                    x,
                    y
                )
        }

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
            val producableId = this.barracks.producableId
            if (event is Event
                && producableId == event.producableId
                && this.barracks.isProduceEnable
                && event.isEnable(this.context, this.barracks)
            ) {
                event.perform(this.context, this.barracks)
                this.pushEventIntoPipes(event)
                this.pipeline.pushEvent(BOnProduceEnablePipe.createEvent(producableId, false))
                return event
            }
            return null
        }

        /**
         * Event.
         */

        class Event(producableId: Long, val x: Int, val y: Int) :
            BOnProduceActionPipe.Event(producableId) {

            fun perform(context: BGameContext, barracks: BHumanBarracks) {
                context.pipeline.pushEvent(BHumanMarine.OnCreateNode.createEvent(barracks.playerId, this.x, this.y))
            }

            fun isEnable(context: BGameContext, barracks: BHumanBarracks): Boolean {
                val controller = context.mapController
                val otherUnit = controller.getUnitByPosition(context, this.x, this.y)
                if (otherUnit !is BEmptyField) {
                    return false
                }
                val barracksLevel = barracks.currentLevel
                val barracksPlayerId = barracks.playerId
                val otherPlayerId = otherUnit.playerId
                if (barracksLevel == FIRST_LEVEL && otherPlayerId == barracksPlayerId) {
                    return true
                }
                val playerHeap = context.storage.getHeap(BPlayerHeap::class.java)
                val barracksOwner = playerHeap[barracksPlayerId]
                if (barracksLevel == SECOND_LEVEL && !barracksOwner.isEnemy(otherPlayerId)) {
                    return true
                }
                if (barracksLevel == THIRD_LEVEL) {
                    return true
                }
                return false
            }
        }
    }