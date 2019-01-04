package com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.BHitPointablePipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsAction.node.BOnHitPointsActionNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BHitPointableHeap

@BContextComponent
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

        abstract fun perform(context: BGameContext): Boolean
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

            override fun perform(context: BGameContext): Boolean {
                val hitPointable = context.storage.getHeap(BHitPointableHeap::class.java)[this.hitPointableId]
                val currentHitPoints = hitPointable.currentHitPoints
                val maxHitPoints = hitPointable.maxHitPoints
                val hasRestore = this.range > 0 && currentHitPoints < maxHitPoints
                if (hasRestore) {
                    val newHitPoints = currentHitPoints + this.range
                    if (newHitPoints < maxHitPoints) {
                        hitPointable.currentHitPoints = newHitPoints
                    } else {
                        hitPointable.currentHitPoints = maxHitPoints
                    }
                }
                return hasRestore
            }
        }

        open class OnDecreasedEvent(hitPointableId: Long, range: Int) :
            Event(hitPointableId, range) {

            override fun perform(context: BGameContext): Boolean {
                val hitPointable = context.storage.getHeap(BHitPointableHeap::class.java)[this.hitPointableId]
                val hasDamage = this.range > 0
                if (hasDamage) {
                    hitPointable.currentHitPoints -= this.range
                }
                return hasDamage
            }
        }

        open class OnChangedEvent(hitPointableId: Long, range: Int) :
            Event(hitPointableId, range) {

            override fun perform(context: BGameContext): Boolean {
                val hitPointable = context.storage.getHeap(BHitPointableHeap::class.java)[this.hitPointableId]
                val maxHitPoints = hitPointable.maxHitPoints
                val hasChanged = this.range in 0..maxHitPoints
                if (hasChanged) {
                    hitPointable.currentHitPoints = this.range
                }
                return hasChanged
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

            override fun perform(context: BGameContext): Boolean {
                val hitPointable = context.storage.getHeap(BHitPointableHeap::class.java)[this.hitPointableId]
                val hasRestore = this.range > 0
                if (hasRestore) {
                    hitPointable.maxHitPoints += this.range
                }
                return hasRestore
            }
        }

        open class OnChangedEvent(hitPointableId: Long, range: Int) :
            Event(hitPointableId, range) {

            override fun perform(context: BGameContext): Boolean {
                val hitPointable = context.storage.getHeap(BHitPointableHeap::class.java)[this.hitPointableId]
                val hasRange = this.range != 0
                if (hasRange) {
                    hitPointable.maxHitPoints = this.range
                    if (hitPointable.currentHitPoints > hitPointable.maxHitPoints) {
                        hitPointable.currentHitPoints = hitPointable.maxHitPoints
                    }
                }
                return hasRange
            }
        }

        open class OnDecreasedEvent(hitPointableId: Long, range: Int) :
            Event(hitPointableId, range) {

            override fun perform(context: BGameContext): Boolean {
                val hitPointable = context.storage.getHeap(BHitPointableHeap::class.java)[this.hitPointableId]
                val hasRange = this.range > 0
                if (hasRange) {
                    hitPointable.maxHitPoints -= this.range
                    if (hitPointable.currentHitPoints > hitPointable.maxHitPoints) {
                        hitPointable.currentHitPoints = hitPointable.maxHitPoints
                    }
                }
                return hasRange
            }
        }
    }
}