package com.orego.battlecrane.bc.api.context.eventPipeline

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BNode
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.BActionPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.attack.BAttackPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.hitPoint.BHitPointPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.level.BLevelPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.produce.BProducePipe
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.unit.BUnitPipe
import com.orego.battlecrane.bc.api.context.eventPipeline.util.EventUtil

class BEventPipeline(context: BGameContext) {

    private var isWorking = false

    private val eventQueue = mutableListOf<BEvent>()

    private val pipeMap = mutableMapOf<String, BPipe>()

    /**
     * Adds root pipes:
     */

    init {
        this.connectPipe(BUnitPipe(context))
        this.connectPipe(BActionPipe(context))
        this.connectPipe(BAttackPipe(context))
        this.connectPipe(BHitPointPipe(context))
        this.connectPipe(BLevelPipe(context))
        this.connectPipe(BProducePipe(context))
    }

    fun pushEvent(event: BEvent?) {
        if (EventUtil.isValid(event)) {
            if (!this.isWorking) {
                this.isWorking = true
                this.pipeMap.values.forEach { it.push(event) }
                this.isWorking = false
                if (!this.eventQueue.isEmpty()) {
                    val nextEvent = this.eventQueue.removeAt(0)
                    this.pushEvent(nextEvent)
                }
            } else {
                this.eventQueue.add(event!!)
            }
        }
    }

    fun connectPipe(pipe: BPipe) {
        this.pipeMap[pipe.name] = pipe
    }

    fun findNode(name: String): BNode? {
        for (pipe in this.pipeMap.values) {
            val node = pipe.findNode(name)
            if (node != null) {
                return node
            }
        }
        return null
    }

    fun findPipe(name: String): BPipe? {
        for (pipe in this.pipeMap.values) {
            val result = pipe.findPipe(name)
            if (result != null) {
                return result
            }
        }
        return null
    }

    operator fun get(name: String) = this.pipeMap[name]
}