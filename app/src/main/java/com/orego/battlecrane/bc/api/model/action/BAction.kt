package com.orego.battlecrane.bc.api.model.action

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.pipe.onCreate.node.BOnCreateActionNode
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer

abstract class BAction protected constructor(
    protected val context: BGameContext,
    open var owner: BPlayer? = null
) {

    abstract fun perform(): Boolean

    /**
     * Factory.
     */

    abstract class Factory(private val eventPipeline: BEventPipeline) {

        fun create() {
            val action = this.createAction()
            val createActionEvent = BEvent(BOnCreateActionNode.NAME, action)
            this.eventPipeline.pushEvent(createActionEvent)
        }

        protected abstract fun createAction(): BAction
    }
}