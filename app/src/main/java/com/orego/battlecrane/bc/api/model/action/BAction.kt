package com.orego.battlecrane.bc.api.model.action

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer

abstract class BAction(
    protected val gameContext: BGameContext,
    protected var owner: BPlayer? = null
) {

    protected abstract fun performAction(): Boolean

    fun perform(): Boolean {
        val isSuccessful = this.performAction()
        if (isSuccessful) {
            this.actionObservers.values.forEach { it.onActionPerformed() }
        }
        return isSuccessful
    }

    val actionObservers = mutableMapOf<Long, Listener>()

    interface Listener {

        fun onActionPerformed()
    }
}