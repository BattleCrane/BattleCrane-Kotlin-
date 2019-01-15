package com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.BHitPointablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.node.BOnHitPointsActionNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BHitPointableHeap

class BOnHitPointsActionPipe(context: BGameContext) : BPipe(context) {

    companion object {

        const val NAME = "ON_HIT_POINTS_ACTION_PIPE"
    }

    override val name = NAME

    init {
        this.placeNode(BOnHitPointsActionNode(context))
    }

    /**
     * Event.
     */

    abstract class Event(val hitPointableId: Long, val range: Int) :
        BHitPointablePipe.Event() {

        abstract fun isEnable(context: BGameContext): Boolean

        abstract fun perform(context: BGameContext)
    }

    object Current {

        fun createOnIncreasedEvent(hitPointableId: Long, range: Int) =
            OnIncreasedEvent(hitPointableId, range)

        fun createOnDecreasedEvent(hitPointableId: Long, range: Int) =
            OnDecreasedEvent(hitPointableId, range)

        fun createOnChangedEvent(hitPointableId: Long, range: Int) =
            OnChangedEvent(hitPointableId, range)

        open class OnIncreasedEvent(hitPointableId: Long, range: Int) :
            Event(hitPointableId, range) {

            override fun isEnable(context: BGameContext): Boolean {
                val hitPointable = context.storage.getHeap(BHitPointableHeap::class.java)[this.hitPointableId]
                return this.range > 0 && hitPointable.currentHitPoints < hitPointable.maxHitPoints
            }

            override fun perform(context: BGameContext) {
                val hitPointable = context.storage.getHeap(BHitPointableHeap::class.java)[this.hitPointableId]
                val currentHitPoints = hitPointable.currentHitPoints
                val maxHitPoints = hitPointable.maxHitPoints
                val newHitPoints = currentHitPoints + this.range
                if (newHitPoints < maxHitPoints) {
                    hitPointable.currentHitPoints = newHitPoints
                } else {
                    hitPointable.currentHitPoints = maxHitPoints
                }
            }
        }

        open class OnDecreasedEvent(hitPointableId: Long, range: Int) :
            Event(hitPointableId, range) {

            override fun isEnable(context: BGameContext) = this.range > 0

            override fun perform(context: BGameContext) {
                val hitPointable = context.storage.getHeap(BHitPointableHeap::class.java)[this.hitPointableId]
                hitPointable.currentHitPoints -= this.range
            }
        }

        open class OnChangedEvent(hitPointableId: Long, range: Int) :
            Event(hitPointableId, range) {

            override fun isEnable(context: BGameContext): Boolean {
                val hitPointable = context.storage.getHeap(BHitPointableHeap::class.java)[this.hitPointableId]
                return this.range in 0..hitPointable.maxHitPoints
            }

            override fun perform(context: BGameContext) {
                val hitPointable = context.storage.getHeap(BHitPointableHeap::class.java)[this.hitPointableId]
                hitPointable.currentHitPoints = this.range
            }
        }
    }

    object Max {

        fun createOnIncreasedEvent(hitPointableId: Long, range: Int) =
            OnIncreasedEvent(hitPointableId, range)

        fun createOnDecreasedEvent(hitPointableId: Long, range: Int) =
            OnDecreasedEvent(hitPointableId, range)

        fun createOnChangedEvent(hitPointableId: Long, range: Int) =
            OnChangedEvent(hitPointableId, range)

        open class OnIncreasedEvent(hitPointableId: Long, range: Int) :
            Event(hitPointableId, range) {

            override fun isEnable(context: BGameContext) = this.range > 0

            override fun perform(context: BGameContext) {
                val hitPointable = context.storage.getHeap(BHitPointableHeap::class.java)[this.hitPointableId]
                hitPointable.maxHitPoints += this.range
            }
        }

        open class OnChangedEvent(hitPointableId: Long, range: Int) :
            Event(hitPointableId, range) {

            override fun isEnable(context: BGameContext) = this.range != 0

            override fun perform(context: BGameContext) {
                val hitPointable = context.storage.getHeap(BHitPointableHeap::class.java)[this.hitPointableId]
                hitPointable.maxHitPoints = this.range
                if (hitPointable.currentHitPoints > hitPointable.maxHitPoints) {
                    hitPointable.currentHitPoints = hitPointable.maxHitPoints
                }
            }
        }

        open class OnDecreasedEvent(hitPointableId: Long, range: Int) :
            Event(hitPointableId, range) {

            override fun isEnable(context: BGameContext) = this.range > 0

            override fun perform(context: BGameContext) {
                val hitPointable = context.storage.getHeap(BHitPointableHeap::class.java)[this.hitPointableId]
                hitPointable.maxHitPoints -= this.range
                if (hitPointable.currentHitPoints > hitPointable.maxHitPoints) {
                    hitPointable.currentHitPoints = hitPointable.maxHitPoints
                }
            }
        }
    }
}