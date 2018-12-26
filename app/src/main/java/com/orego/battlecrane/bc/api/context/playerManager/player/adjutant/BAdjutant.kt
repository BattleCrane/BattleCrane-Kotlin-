package com.orego.battlecrane.bc.api.context.playerManager.player.adjutant;

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.action.BAction

abstract class BAdjutant(
    protected val context: BGameContext,
    protected val ownerId: Long,
    protected val bonusFactories: MutableSet<Long>
) {

    open val resourceManager = ResourceManager()

    abstract fun onGameStarted()

    abstract fun onTurnStarted()

    abstract fun onTurnEnded()

    open inner class ResourceManager {

        var currentInfluenceCount = 0

        var receivedInfluenceCount = 0

        val buildingActions = mutableSetOf<BAction>()

        val trainActions = mutableSetOf<BAction>()
    }

    /**
     * Builder.
     */

    abstract class Builder {

        val bonusFactories = mutableSetOf<Long>()

        abstract fun build(context: BGameContext, owner: Long): BAdjutant
    }
}