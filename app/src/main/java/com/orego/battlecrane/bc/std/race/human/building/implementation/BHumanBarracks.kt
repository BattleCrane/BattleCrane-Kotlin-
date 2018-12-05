package com.orego.battlecrane.bc.std.race.human.building.implementation

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.mapManager.cell.BCell
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.api.model.contract.BHealthable
import com.orego.battlecrane.bc.api.model.contract.BLevelable
import com.orego.battlecrane.bc.std.race.human.building.BHumanBuilding

class BHumanBarracks(gameContext: BGameContext, owner: BPlayer) : BUnit(gameContext, owner),
    BHumanBuilding, BHealthable,
    BLevelable {

    override fun isPlaced(position: BCell): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 2

        private const val DEFAULT_HORIZONTAL_SIDE = 1

        private const val DEFAULT_MAX_HEALTH = 1

        private const val DEFAULT_LEVEL = 1

        private const val DEFAULT_MAX_LEVEL = 3
    }

    /**
     * Properties.
     */

    override val verticalSide =
        DEFAULT_VERTICAL_SIDE

    override val horizontalSide =
        DEFAULT_HORIZONTAL_SIDE

    override var currentHealth =
        DEFAULT_MAX_HEALTH

    override var maxHealth =
        DEFAULT_MAX_HEALTH

    override var currentLevel =
        DEFAULT_LEVEL

    override var maxLevel =
        DEFAULT_MAX_LEVEL

    /**
     * Observers.
     */

    override val decreaseHealthObserver: MutableMap<Long, BHealthable.Listener> = mutableMapOf()

    override val increaseHealthObserver: MutableMap<Long, BHealthable.Listener> = mutableMapOf()

    override val levelUpObserver: MutableMap<Long, BLevelable.LevelListener> = mutableMapOf()

    override val levelDownObserver: MutableMap<Long, BLevelable.LevelListener> = mutableMapOf()
}