package com.orego.battlecrane.bc.std.race.human.unit.building.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.contract.BHitPointable
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding

class BHumanWall(gameContext: BGameContext, owner: BPlayer) : BHumanBuilding(gameContext, owner),
    BHitPointable {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 1

        private const val DEFAULT_HORIZONTAL_SIDE = 1

        private const val DEFAULT_MAX_HEALTH = 4
    }

    /**
     * Properties.
     */

    override val verticalSize = DEFAULT_VERTICAL_SIDE

    override val horizontalSize = DEFAULT_HORIZONTAL_SIDE

    override var currentHitPoints = DEFAULT_MAX_HEALTH

    override var maxHitPoints = DEFAULT_MAX_HEALTH

    /**
     * Observers.
     */

    override val decreaseHitPointsObserver: MutableMap<Long, BHitPointable.Listener> = mutableMapOf()

    override val increaseHitPointsObserver: MutableMap<Long, BHitPointable.Listener> = mutableMapOf()
}