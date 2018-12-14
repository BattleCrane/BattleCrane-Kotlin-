package com.orego.battlecrane.bc.std.race.human.building.implementation

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.model.contract.BAttackable
import com.orego.battlecrane.bc.api.model.contract.BHitPointable
import com.orego.battlecrane.bc.api.model.contract.BLevelable
import com.orego.battlecrane.bc.std.race.human.building.BHumanBuilding

class BHumanTurret(context: BGameContext, owner: BPlayer) : BHumanBuilding(context, owner), BHitPointable,
    BLevelable, BAttackable {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 2

        private const val DEFAULT_HORIZONTAL_SIDE = 1

        private const val DEFAULT_MAX_HEALTH = 2

        private const val DEFAULT_LEVEL = 1

        private const val DEFAULT_MAX_LEVEL = 2

        private const val DEFAULT_DAMAGE = 1

        private const val DEFAULT_ATTACK_TIMES = 1

        private const val DEFAULT_IS_ATTACK_ENABLE = false

        private const val DEFAULT_RADIUS_ATTACK = 2
    }

    /**
     * Properties.
     */

    override val verticalSide = DEFAULT_VERTICAL_SIDE

    override val horizontalSide = DEFAULT_HORIZONTAL_SIDE

    override var currentHitPoints = DEFAULT_MAX_HEALTH

    override var maxHitPoints = DEFAULT_MAX_HEALTH

    override var currentLevel = DEFAULT_LEVEL

    override var maxLevel = DEFAULT_MAX_LEVEL

    override var damage = DEFAULT_DAMAGE

    override var isAttackEnable = DEFAULT_IS_ATTACK_ENABLE

    var radiusAttack = DEFAULT_RADIUS_ATTACK

    var lastAttack = Attack()

    //TODO: CREATING PIPELINE!!!
    init {
        this.lastAttack.perform()
    }

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

    override fun getAttackAction(): BAction? {
        return if (this.isAttackEnable) {
            Attack()
        } else {
            null
        }
    }

    override fun onTurnStarted() {
        this.switchAttackEnable(true)
        this.lastAttack = Attack()
        this.lastAttack.perform()

    }

    override fun onTurnEnded() {
        this.switchAttackEnable(false)
    }

    /**
     * Functions.
     */

    fun attackInRadius() {
        val mapManager = this.context.mapManager
        val playerManager = this.context.playerManager
        //"Пирамидальный сдвиг": с каждой итерируется по горизонтали с формулой 2i -1
        var countShift = 0
        val x = this.pivot!!.x
        val y = this.pivot!!.y
        for (i in x - this.radiusAttack until x + this.radiusAttack + 1) {
            for (j in y - countShift until y + 1 + countShift) {
                if (mapManager.inBounds(i, j)) {
                    val currentUnit = mapManager.getUnitByPosition(i, j)
                    val isEnemy = playerManager.isEnemies(this, currentUnit)
                    if (isEnemy && currentUnit is BHitPointable) {
                        this.attack(currentUnit)
                    }
                }
            }
            countShift++
            if (i >= x) {
                //Перетягивание countShift--
                countShift -= 2
            }
        }
    }

    inner class Attack : BAction(this.context, this.owner) {

        override fun performAction(): Boolean {
            this@BHumanTurret.attackInRadius()
            this@BHumanTurret.switchAttackEnable(false)
            return true
        }
    }
}