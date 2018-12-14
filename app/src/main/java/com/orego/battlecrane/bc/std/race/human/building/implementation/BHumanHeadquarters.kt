package com.orego.battlecrane.bc.std.race.human.building.implementation

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.contract.BAttackable
import com.orego.battlecrane.bc.api.model.contract.BHitPointable
import com.orego.battlecrane.bc.api.model.contract.BLevelable
import com.orego.battlecrane.bc.std.race.human.building.BHumanBuilding

class BHumanHeadquarters(gameContext: BGameContext, owner: BPlayer) : BHumanBuilding(gameContext, owner),
    BHitPointable, BLevelable, BAttackable {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 2

        private const val DEFAULT_HORIZONTAL_SIDE = 2

        private const val DEFAULT_MAX_HEALTH = 8

        private const val DEFAULT_LEVEL = 1

        private const val DEFAULT_MAX_LEVEL = 2

        private const val DEFAULT_DAMAGE = 1

        private const val DEFAULT_ATTACK_TIMES = 2

        private const val DEFAULT_IS_ATTACK_ENABLE = false
    }

    /**
     * Property.
     */

    override val verticalSide =
        DEFAULT_VERTICAL_SIDE

    override val horizontalSide =
        DEFAULT_HORIZONTAL_SIDE

    override var currentHitPoints =
        DEFAULT_MAX_HEALTH

    override var maxHitPoints =
        DEFAULT_MAX_HEALTH

    override var currentLevel =
        DEFAULT_LEVEL

    override var maxLevel =
        DEFAULT_MAX_LEVEL

    override var damage =
        DEFAULT_DAMAGE

    override var isReadyToAttack =
        DEFAULT_ATTACK_TIMES

    override var isAttackEnable =
        DEFAULT_IS_ATTACK_ENABLE

    /**
     * Observers.
     */

    override val decreaseHitPointsObserver: MutableMap<Long, BHitPointable.Listener> = mutableMapOf()

    override val increaseHitPointsObserver: MutableMap<Long, BHitPointable.Listener> = mutableMapOf()

    override val levelUpObserver: MutableMap<Long, BLevelable.Listener> = mutableMapOf()

    override val levelDownObserver: MutableMap<Long, BLevelable.Listener> = mutableMapOf()

    override val attackObserver: MutableMap<Long, BAttackable.AttackListener> = mutableMapOf()

    override val damageObserver: MutableMap<Long, BAttackable.DamageListener> = mutableMapOf()

    override val attackEnableObserver: MutableMap<Long, BAttackable.AttackEnableListener> = mutableMapOf()
}