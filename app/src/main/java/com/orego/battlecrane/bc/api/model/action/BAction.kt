package com.orego.battlecrane.bc.api.model.action

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.BEvent
import com.orego.battlecrane.bc.api.context.eventPipeline.BContract
import com.orego.battlecrane.bc.api.context.eventPipeline.BEventPipeline
import com.orego.battlecrane.bc.api.context.eventPipeline.pipe.action.node.BOnCreateActionNode
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer

abstract class BAction protected constructor(
    protected val context: BGameContext,
    var owner: BPlayer? = null
) {

    protected abstract fun performAction(): Boolean

    fun perform(): Boolean {
        val isSuccessful = this.performAction()
        if (isSuccessful) {
            this.actionObservers.values.forEach { it.onActionPerformed(this) }
        }
        return isSuccessful
    }

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