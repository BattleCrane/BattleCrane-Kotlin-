package com.orego.battlecrane.bc.api.model.adjutant;

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.contract.BAction

open class BAdjutant protected constructor(
    protected val context: BGameContext,
    protected val ownerId: Long,
    protected val bonusFactories: MutableSet<Long>
) {

    open val resourceManager = ResourceManager()

    open class ResourceManager {

        var currentInfluenceCount = 0

        var receivedInfluenceCount = 0

        val buildingActions = mutableSetOf<Long>()

        val trainActions = mutableSetOf<Long>()
    }

    /**
     * Builder.
     */

    open class Builder {

        val bonusFactories = mutableSetOf<Long>()

        open fun build(context: BGameContext, owner: Long) =
            BAdjutant(context, owner, this.bonusFactories)
    }
}