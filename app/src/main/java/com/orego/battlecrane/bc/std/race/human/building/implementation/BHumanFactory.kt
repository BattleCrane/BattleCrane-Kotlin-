package com.orego.battlecrane.bc.std.race.human.building.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.model.contract.BHitPointable
import com.orego.battlecrane.bc.api.model.contract.BLevelable
import com.orego.battlecrane.bc.api.model.contract.BProducable
import com.orego.battlecrane.bc.api.model.contract.BTargetable
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.bc.std.race.human.action.BHumanAction
import com.orego.battlecrane.bc.std.race.human.building.BHumanBuilding
import com.orego.battlecrane.bc.std.race.human.vehicle.implementation.BHumanTank

class BHumanFactory(context: BGameContext, owner: BPlayer) : BHumanBuilding(context, owner),
    BHitPointable, BLevelable, BProducable {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 3

        private const val DEFAULT_HORIZONTAL_SIDE = 2

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

    /**
     * Observers.
     */

    override val decreaseHitPointsObserver: MutableMap<Long, BHitPointable.Listener> = mutableMapOf()

    override val increaseHitPointsObserver: MutableMap<Long, BHitPointable.Listener> = mutableMapOf()

    override val levelUpObserver: MutableMap<Long, BLevelable.Listener> = mutableMapOf()

    override val levelDownObserver: MutableMap<Long, BLevelable.Listener> = mutableMapOf()

    override var isProduceStateChangedObserver: MutableMap<Long, BProducable.Listener> = mutableMapOf()

    /**
     * Companions.
     */

    private val pipeline by lazy { this.context.pipeline }

    val trainTankLvl1Factory = TrainTankLvl1Factory()

    val trainTankLvl2Factory = TrainTankLvl2Factory()

    val trainTankLvl3Factory = TrainTankLvl3Factory()


    /**
     * Lifecycle.
     */

    override fun onTurnStarted() {
        this.switchProduceEnable(true)
    }

    override fun onTurnEnded() {
        this.switchProduceEnable(false)
    }

    /**
     * Producer function.
     */

    override fun getProduceActions(context: BGameContext, owner: BPlayer) = mutableSetOf<BAction>()
        .also {
            if (this.isProduceEnable) {
                it.add(this.trainTankLvl1Factory.create())
                if (this.currentLevel > 1) {
                    it.add(this.trainTankLvl2Factory.create())
                    if (this.currentLevel > 2) {
                        it.add(this.trainTankLvl3Factory.create())
                    }
                }
            }
        }

    /**
     * Action.
     */

    inner class TrainTank(private val cond: (BUnit) -> Boolean) : BHumanAction(this.context, this.owner!!),
        BTargetable {

        override var targetPosition: BPoint? = null

        override fun performAction(): Boolean {
            if (this.targetPosition != null) {
                val tank = BHumanTank(this.context, this.owner!!)
                val manager = this.context.mapManager
                val unit = manager.getUnitByPosition(this.targetPosition)
                if (unit is BEmptyField && this.cond(unit)) {
                    val isSuccessful = manager.createUnit(tank, this.targetPosition)
                    if (isSuccessful) {
                        this@BHumanFactory.switchProduceEnable(false)
                    }
                    return isSuccessful
                }
            }
            return false
        }
    }

    /**
     * Factories.
     */

    inner class TrainTankLvl1Factory : BAction.Factory(this.pipeline) {

        override fun createAction() =
            TrainTank { unit -> this@BHumanFactory.owner!!.owns(unit) }
    }

    inner class TrainTankLvl2Factory : BAction.Factory(this.pipeline) {

        override fun createAction() =
            TrainTank { unit -> !this@BHumanFactory.owner!!.isEnemy(unit.owner) }
    }

    inner class TrainTankLvl3Factory : BAction.Factory(this.pipeline) {

        override fun createAction() =
            TrainTank { true }
    }
}