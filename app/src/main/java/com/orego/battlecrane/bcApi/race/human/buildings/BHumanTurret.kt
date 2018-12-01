package com.orego.battlecrane.bcApi.race.human.buildings

import com.orego.battlecrane.bcApi.manager.BGameManager
import com.orego.battlecrane.bcApi.unit.BIdGenerator
import com.orego.battlecrane.bcApi.unit.BUnit
import com.orego.battlecrane.bcApi.unit.contract.BAttackable
import com.orego.battlecrane.bcApi.unit.contract.BHealthable
import com.orego.battlecrane.bcApi.unit.contract.BLevelable

class BHumanTurret(manager: BGameManager, owner: Int) : BUnit(manager, owner), BHealthable, BLevelable, BAttackable {

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

    override var currentHealth = DEFAULT_MAX_HEALTH

    override var maxHealth = DEFAULT_MAX_HEALTH

    override var currentLevel = DEFAULT_LEVEL

    override var maxLevel = DEFAULT_MAX_LEVEL

    override var damage = DEFAULT_DAMAGE

    override var attackTimes = DEFAULT_ATTACK_TIMES

    override var isAttackEnable = DEFAULT_IS_ATTACK_ENABLE

    var radiusAttack = DEFAULT_RADIUS_ATTACK

    /**
     * Observers.
     */

    override val decreaseHealthObserver: MutableMap<Long, BHealthable.HealthListener> = mutableMapOf()

    override val increaseHealthObserver: MutableMap<Long, BHealthable.HealthListener> = mutableMapOf()

    override val levelUpObserver: MutableMap<Long, BLevelable.LevelListener> = mutableMapOf()

    override val levelDownObserver: MutableMap<Long, BLevelable.LevelListener> = mutableMapOf()

    override val attackObserver: MutableMap<Long, BAttackable.AttackListener> = mutableMapOf()

    override val damageObserver: MutableMap<Long, BAttackable.DamageListener> = mutableMapOf()

    override val attackEnableObserver: MutableMap<Long, BAttackable.AttackEnableListener> = mutableMapOf()

    /**
     * Init.
     */

    init {
        this.onCreateObserver[BIdGenerator.generateActionId()] = OnCreateTurretListener()
    }

    /**
     * Functions.
     */

    fun attackInRadius() {
        val mapManager = this.gameManager.mapManager
        val playerManager = this.gameManager.playerManager
        //"Пирамидальный сдвиг": с каждой итерируется по горизонтали с формулой 2i -1
        var countShift = 0
        val x = this.pivot!!.x
        val y = this.pivot!!.y
        for (i in x - this.radiusAttack until x + this.radiusAttack + 1) {
            for (j in y - countShift until y + 1 + countShift) {
                if (mapManager.inBounds(i, j)) {
                    val currentUnit = mapManager.getUnitByPosition(i, j)
                    if (playerManager.isEnemies(this, currentUnit)) {
                        if (currentUnit is BHealthable) {
                            this.attack(currentUnit)
                        }
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

    private inner class OnCreateTurretListener : OnCreateListener {

        override fun onCreate(unit: BUnit) {
            this@BHumanTurret.attackInRadius()
        }
    }
}