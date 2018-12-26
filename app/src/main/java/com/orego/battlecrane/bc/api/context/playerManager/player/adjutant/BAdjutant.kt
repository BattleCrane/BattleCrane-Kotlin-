package com.orego.battlecrane.bc.api.context.playerManager.player.adjutant;

import com.orego.battlecrane.bc.api.context.BGameContext

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

        val buildingActions = mutableSetOf<Long>()

        val trainActions = mutableSetOf<Long>()
    }

    /**
     * Builder.
     */

    abstract class Builder {

        val bonusFactories = mutableSetOf<Long>()

        abstract fun build(context: BGameContext, owner: Long): BAdjutant
    }
}