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

    val trainMarineLvl1Factory = TrainMarineLvl1Factory()

    val trainMarineLvl2Factory = TrainMarineLvl2Factory()

    val trainMarineLvl3Factory = TrainMarineLvl3Factory()

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
     * Produce function.
     */

    override fun getProduceActions(context: BGameContext, owner: BPlayer) = mutableSetOf<BAction>()
        .also { set ->
            if (this.isProduceEnable) {
                this.trainMarineLvl1Factory.create()?.let { set.add(it) }
                if (this.currentLevel > 1) {
                    this.trainMarineLvl2Factory.create()?.let { set.add(it) }
                    if (this.currentLevel > 2) {
                        this.trainMarineLvl3Factory.create()?.let { set.add(it) }
                    }
                }
            }
        }

    /**
     * Action.
     */

    inner class TrainMarine(private val createCond: (BUnit) -> Boolean) :
        BHumanAction(this.context, this.owner!!), BTargetable {

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

    /**
     * Factories.
     */

    inner class TrainMarineLvl1Factory : BAction.Factory(this.pipeline) {

        override fun createAction() =
            TrainMarine { unit -> this@BHumanBarracks.owner!!.owns(unit) }
    }

    inner class TrainMarineLvl2Factory : BAction.Factory(this.pipeline) {

        override fun createAction() =
            TrainMarine { unit -> !this@BHumanBarracks.owner!!.isEnemy(unit.owner) }
    }

    inner class TrainMarineLvl3Factory : BAction.Factory(this.pipeline) {

        override fun createAction() =
            TrainMarine { true }
    }
}