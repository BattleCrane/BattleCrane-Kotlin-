package com.orego.battlecrane.bc.api.model.action

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.BEventContract
import com.orego.battlecrane.bc.api.context.pipeline.BPipeline
import com.orego.battlecrane.bc.api.context.pipeline.isValidEvent
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer

abstract class BAction protected constructor(
    protected val context: BGameContext,
    protected var owner: BPlayer? = null
) {

    protected abstract fun performAction(): Boolean

    fun perform(): Boolean {
        val isSuccessful = this.performAction()
        if (isSuccessful) {
            this.actionObservers.values.forEach { it.onActionPerformed(this) }
        }
        return isSuccessful
    }

    val actionObservers = mutableMapOf<Long, Listener>()

    interface Listener {

        fun onActionPerformed(action: BAction)
    }

    /**
     * Factory.
     */

    abstract class Factory(private val pipeline: BPipeline) {

        fun create(): BAction? {
            val action = this.createAction()
            val createActionEvent = BEvent(BEventContract.CREATE, action)
            this.pipeline.handle(createActionEvent)
            return if (isValidEvent(createActionEvent)
                && createActionEvent.name == BEventContract.CREATE
                && createActionEvent.any is BAction
            ) {
                createActionEvent.any
            } else {
                null
            }
        }

        protected abstract fun createAction(): BAction
    }
}