package com.orego.battlecrane.bc.api.model.adjutant;

open class BAdjutant(val playerId: Long) {

    open val resourceManager = ResourceManager()

    open class ResourceManager {

        var currentInfluenceCount = 0

        var receivedInfluenceCount = 0
    }

    /**
     * Builder.
     */

    open class Builder {

        open fun build(playerId: Long) = BAdjutant(playerId)
    }
}