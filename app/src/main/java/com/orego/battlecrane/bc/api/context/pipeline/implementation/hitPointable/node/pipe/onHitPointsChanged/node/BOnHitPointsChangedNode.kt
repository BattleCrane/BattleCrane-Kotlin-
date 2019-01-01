package com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsChanged.node

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.context.BContextComponent
import com.orego.battlecrane.bc.api.context.pipeline.implementation.hitPointable.node.pipe.onHitPointsChanged.BOnHitPointsChangedPipe
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BHitPointableHeap

@BContextComponent
class BOnHitPointsChangedNode(context: BGameContext) : BNode(context) {

    companion object {

        const val NAME = "ON_HIT_POINTS_CHANGED_NODE"
    }

    override val name = NAME

    /**
     * Context.
     */

    private val hitPointableHeap by lazy {
        this.context.storage.getHeap(BHitPointableHeap::class.java)
    }

    /**
     * Handler functon.
     */

    private val decreaseHitPointsFunc: (Long, Int) -> Boolean = { hitPointableId, damage ->
        val hasDamage = damage > 0
        if (hasDamage) {
            val hitPointable = this.hitPointableHeap[hitPointableId]!!
            hitPointable.currentHitPoints -= damage
        }
        hasDamage
    }

    private val increaseHitPointsFunc: (Long, Int) -> Boolean = { hitPointableId, restore ->
        val hitPointable = this.hitPointableHeap[hitPointableId]!!
        val currentHitPoints = hitPointable.currentHitPoints
        val maxHitPoints = hitPointable.maxHitPoints
        val hasRestore = restore > 0 && currentHitPoints < maxHitPoints
        if (hasRestore) {
            val newHitPoints = currentHitPoints + restore
            if (newHitPoints < maxHitPoints) {
                hitPointable.currentHitPoints = newHitPoints
            } else {
                hitPointable.currentHitPoints = maxHitPoints
            }
        }
        hasRestore
    }

    private val changeHitPointsFunc: (Long, Int) -> Boolean = { hitPointableId, newHitPointsValue ->
        val hitPointable = this.hitPointableHeap[hitPointableId]!!
        val maxHitPoints = hitPointable.maxHitPoints
        val hasChanged = newHitPointsValue in 0..maxHitPoints
        if (hasChanged) {
            hitPointable.currentHitPoints = newHitPointsValue
        }
        hasChanged
    }

    /**
     * Function map.
     */

    val eventHandlerFuncMap = mutableMapOf<Class<*>, (Long, Int) -> Boolean>(
        BOnHitPointsChangedPipe.OnHitPointsIncreasedEvent::class.java to this.increaseHitPointsFunc,
        BOnHitPointsChangedPipe.OnHitPointsDecreasedEvent::class.java to this.decreaseHitPointsFunc,
        BOnHitPointsChangedPipe.OnHitPointsChangedEvent::class.java to this.changeHitPointsFunc
    )

    override fun handle(event: BEvent): BEvent? {
        val handlerFunc = this.eventHandlerFuncMap[event::class.java]
        if (handlerFunc!= null && event is BOnHitPointsChangedPipe.OnHitPointsActionEvent) {
            if (handlerFunc(event.hitPointableId, event.range)) {
                this.pushEventIntoPipes(event)
                return event
            }
        }
        return null
    }
}