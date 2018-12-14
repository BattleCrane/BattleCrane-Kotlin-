package com.orego.battlecrane.bc.std.race.human.building.implementation

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.model.contract.BHitPointable
import com.orego.battlecrane.bc.api.model.contract.BLevelable
import com.orego.battlecrane.bc.api.model.contract.BProducable
import com.orego.battlecrane.bc.api.model.contract.BTargetable
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.action.BHumanAction
import com.orego.battlecrane.bc.std.race.human.building.BHumanBuilding
import com.orego.battlecrane.bc.std.race.human.infantry.implementation.BHumanMarine

class BHumanBarracks(context: BGameContext, owner: BPlayer) : BHumanBuilding(context, owner), BHitPointable,
    BLevelable, BProducable {

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

    override val verticalSide = DEFAULT_VERTICAL_SIDE

    override val horizontalSide = DEFAULT_HORIZONTAL_SIDE

    override var currentHitPoints = DEFAULT_MAX_HEALTH

    override var maxHitPoints = DEFAULT_MAX_HEALTH

    override var currentLevel = DEFAULT_LEVEL

    override var maxLevel = DEFAULT_MAX_LEVEL

    override var isProduceEnable = false

    override val decreaseHitPointsObserver: MutableMap<Long, BHitPointable.Listener> = mutableMapOf()

    override val increaseHitPointsObserver: MutableMap<Long, BHitPointable.Listener> = mutableMapOf()

    override val levelUpObserver: MutableMap<Long, BLevelable.Listener> = mutableMapOf()

    override val levelDownObserver: MutableMap<Long, BLevelable.Listener> = mutableMapOf()

    /**
     * Observers.
     */

    override var isProduceStateChangedObserver: MutableMap<Long, BProducable.Listener> = mutableMapOf()

    override fun getProduceActions(context: BGameContext, owner: BPlayer) = mutableSetOf<BAction>()
        .also {
            if (this.isProduceEnable) {
                it.add(TrainMarine { unit -> this.owner!!.owns(unit) })
                if (this.currentLevel > 1) {
                    it.add(TrainMarine { unit -> !this.owner!!.isEnemy(unit.owner) })
                    if (this.currentLevel > 2) {
                        it.add(TrainMarine { true })
                    }
                }
            }
        }

    override fun onTurnStarted() {
        this.switchProduceEnable(true)
    }

    override fun onTurnEnded() {
        this.switchProduceEnable(false)
    }

    /**
     * Action.
     */

    inner class TrainMarine(private val createCond: (BUnit) -> Boolean) : BHumanAction(this.context, this.owner!!),
        BTargetable {

        override var targetPosition: BPoint? = null

        override fun performAction(): Boolean {
            if (this.targetPosition != null) {
                val marine = BHumanMarine(this.context, this.owner!!)
                val manager = this.context.mapManager
                val unit = manager.getUnitByPosition(this.targetPosition)
                if (this.createCond(unit)) {
                    val isSuccessful = manager.createUnit(marine, this.targetPosition)
                    if (isSuccessful) {
                        this@BHumanBarracks.switchProduceEnable(false)
                    }
                    return isSuccessful
                }
            }
            return false
        }
    }
}