package com.orego.battlecrane.bc.api.manager.mechanics.adjutant;

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction

abstract class BAdjutant(
    protected val context: BGameContext,
    protected val owner: BPlayer,
    protected val bonusFactories: MutableSet<BAction.Factory>
) {
    abstract fun onGameStarted()

    abstract fun onTurnStarted()

    abstract fun onTurnEnded()

    abstract inner class ResourceManager {

        var currentInfluenceCount = 0

        var receivedInfluenceCount = 0

        val buildingActions = mutableSetOf<BAction>()

        val armyActions = mutableSetOf<BAction>()
    }

    /**
     * Builder.
     */

    interface Builder {

        fun build(context: BGameContext, owner: BPlayer): BAdjutant
    }
}