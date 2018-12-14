package com.orego.battlecrane.bc.std.race.human.infantry.implementation

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.contract.BAttackable
import com.orego.battlecrane.bc.api.model.contract.BHitPointable
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.infantry.BHumanInfantry

open class BHumanMarine(context: BGameContext, owner: BPlayer) : BUnit(context, owner),
    BHumanInfantry, BHitPointable, BAttackable {

    override fun isPlaced(position: BPoint): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 1

        private const val DEFAULT_HORIZONTAL_SIDE = 1

        private const val DEFAULT_MAX_HEALTH = 1

        private const val DEFAULT_DAMAGE = 1

        private const val DEFAULT_ATTACK_TIMES = 1

        private const val DEFAULT_IS_ATTACK_ENABLE = true
    }

    /**
     * Properties.
     */

    final override val verticalSide =
        DEFAULT_VERTICAL_SIDE

    final override val horizontalSide =
        DEFAULT_HORIZONTAL_SIDE

    final override var currentHitPoints =
        DEFAULT_MAX_HEALTH

    final override var maxHitPoints =
        DEFAULT_MAX_HEALTH

    final override var damage =
        DEFAULT_DAMAGE

    final override var isReadyToAttack =
        DEFAULT_ATTACK_TIMES

    final override var isAttackEnable =
        DEFAULT_IS_ATTACK_ENABLE

    /**
     * Observers.
     */

    final override val decreaseHitPointsObserver: MutableMap<Long, BHitPointable.Listener> = mutableMapOf()

    final override val increaseHitPointsObserver: MutableMap<Long, BHitPointable.Listener> = mutableMapOf()

    final override val damageObserver: MutableMap<Long, BAttackable.DamageListener> = mutableMapOf()

    final override val attackObserver: MutableMap<Long, BAttackable.AttackListener> = mutableMapOf()

    final override val attackEnableObserver: MutableMap<Long, BAttackable.AttackEnableListener> = mutableMapOf()
}