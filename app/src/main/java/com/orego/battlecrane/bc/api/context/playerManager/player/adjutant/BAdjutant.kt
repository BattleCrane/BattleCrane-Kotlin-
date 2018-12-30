package com.orego.battlecrane.bc.api.context.playerManager.player.adjutant;

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.action.BAction

abstract class BAdjutant(
    protected val context: BGameContext,
    protected val ownerId: Long,
    protected val bonusFactories: MutableSet<Long>
) {

    open val resourceManager = ResourceManager()

    open inner class ResourceManager {

        var currentInfluenceCount = 0

        var receivedInfluenceCount = 0

        open val buildingActions = mutableSetOf<BAction>()

        open val trainActions = mutableSetOf<BAction>()
    }

    /**
     * Builder.
     */

    abstract class Builder {

        val bonusFactories = mutableSetOf<Long>()

        abstract fun build(context: BGameContext, owner: Long): BAdjutant
    }
}