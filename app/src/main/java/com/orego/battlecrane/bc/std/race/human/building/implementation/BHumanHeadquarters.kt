package com.orego.battlecrane.bc.std.race.human.building.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.model.contract.BHitPointable
import com.orego.battlecrane.bc.api.model.contract.BLevelable
import com.orego.battlecrane.bc.api.model.contract.BProducable
import com.orego.battlecrane.bc.api.model.contract.BTargetable
import com.orego.battlecrane.bc.std.race.human.action.BHumanAction
import com.orego.battlecrane.bc.std.race.human.building.BHumanBuilding

class BHumanHeadquarters(context: BGameContext, owner: BPlayer) : BHumanBuilding(context, owner),
    BHitPointable, BLevelable, BProducable {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 2

        private const val DEFAULT_HORIZONTAL_SIDE = 2

        private const val DEFAULT_MAX_HEALTH = 8

        private const val DEFAULT_LEVEL = 1

        private const val DEFAULT_MAX_LEVEL = 2

        private const val HEADQUARTERS_BUILD_ABILITY = 1

        private const val GENERATOR_LIMIT = 2

        private const val DEFAULT_BUILDING_UPGRADE = 1
    }

    /**
     * Properties.
     */

    override val verticalSize = DEFAULT_VERTICAL_SIDE

    override val horizontalSize = DEFAULT_HORIZONTAL_SIDE

    override var currentHitPoints = DEFAULT_MAX_HEALTH

    override var maxHitPoints = DEFAULT_MAX_HEALTH

    override var currentLevel = DEFAULT_LEVEL

    override var maxLevel = DEFAULT_MAX_LEVEL

    override var isProduceEnable: Boolean
        get () = this.buildDeveloper.buildActionCount > 0
        set(isEnable) {
            if (isEnable) {
                this.buildDeveloper.initBuildActionCount()
            }
        }

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

    val buildDeveloper = BuildDeveloper()

    val buildBarracksFactory = BuildBarracksFactory()

    val buildTurretFactory = BuildTurretFactory()

    val buildWallFactory = BuildWallFactory()

    val buildFactoryFactory = BuildFactoryFactory()

    val buildGeneratorFactory = BuildGeneratorFactory()

    val upgrageBuildingFactory = UpgrageBuildingFactory()

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
        .also { set ->
            if (this.isProduceEnable) {
                this.buildBarracksFactory.create()?.let { set.add(it) }
                this.buildTurretFactory.create()?.let { set.add(it) }
                this.buildWallFactory.create()?.let { set.add(it) }
                if (this.buildDeveloper.canFactoryBuild()) {
                    this.buildFactoryFactory.create()?.let { set.add(it) }
                }
                if (this.buildDeveloper.canGeneratorBuild()) {
                    this.buildGeneratorFactory.create()?.let { set.add(it) }
                }
                if (this.buildDeveloper.canUpgrade()) {
                    this.upgrageBuildingFactory.create()?.let { set.add(it) }
                }
            }
        }

    /**
     * Actions.
     */

    abstract inner class Build : BHumanAction(this.context, this.owner!!),
        BTargetable {

        override var targetPosition: BPoint? = null

        protected abstract fun buildUnit(): BHumanBuilding

        override fun performAction(): Boolean {
            if (this.targetPosition != null && this.owner != null) {
                val unit = this.buildUnit()
                val manager = this.context.mapManager
                val isSuccessful = manager.createUnit(unit, this.targetPosition)
                if (isSuccessful) {
                    this@BHumanHeadquarters.buildDeveloper.buildActionCount--
                }
                return isSuccessful
            }
            return false
        }
    }

    inner class UpgradeBuilding : BHumanAction(this.context, this.owner!!), BTargetable {

        override var targetPosition: BPoint? = null

        override fun performAction(): Boolean {
            if (this.targetPosition != null && this.owner != null) {
                val manager = this.context.mapManager
                val unit = manager.getUnitByPosition(this.targetPosition)
                if (unit is BLevelable && unit is BHumanBuilding) {
                    return unit.increaseLevel(DEFAULT_BUILDING_UPGRADE)
                }
            }
            return false
        }
    }

    inner class BuildBarracksFactory : BAction.Factory(this.pipeline) {

        override fun createAction() = Action()

        inner class Action : Build() {

            override fun buildUnit() =
                BHumanBarracks(this@BHumanHeadquarters.context, this@BHumanHeadquarters.owner!!)
        }
    }

    inner class BuildTurretFactory : BAction.Factory(this.pipeline) {

        override fun createAction() =  Action()

        inner class Action : Build() {

            override fun buildUnit() =
                BHumanTurret(this@BHumanHeadquarters.context, this@BHumanHeadquarters.owner!!)
        }
    }

    inner class BuildWallFactory : BAction.Factory(this.pipeline) {

        override fun createAction() = Action()

        inner class Action : Build() {

            override fun buildUnit() =
                BHumanWall(this@BHumanHeadquarters.context, this@BHumanHeadquarters.owner!!)
        }
    }

    inner class BuildFactoryFactory : BAction.Factory(this.pipeline) {

        override fun createAction() = Action()

        inner class Action : Build() {

            override fun buildUnit() =
                BHumanFactory(this@BHumanHeadquarters.context, this@BHumanHeadquarters.owner!!)
        }
    }

    inner class BuildGeneratorFactory : BAction.Factory(this.pipeline) {

        override fun createAction() =  Action()

        inner class Action : Build() {

            override fun buildUnit() =
                BHumanGenerator(this@BHumanHeadquarters.context, this@BHumanHeadquarters.owner!!)
        }
    }

    inner class UpgrageBuildingFactory : BAction.Factory(this.pipeline) {

        override fun createAction() = UpgradeBuilding()
    }

    /**
     * Build developer.
     */

    inner class BuildDeveloper {

        private val unitHeap by lazy {
            this@BHumanHeadquarters.context.mapManager.unitHeap.values
        }

        var buildActionCount = 0

        fun initBuildActionCount() {
            val owner = this@BHumanHeadquarters.owner!!
            var abilityCount = HEADQUARTERS_BUILD_ABILITY
            this.unitHeap.forEach { unit ->
                if (owner.owns(unit) && unit is BHumanGenerator) {
                    abilityCount++
                }
            }
            this.buildActionCount = abilityCount
        }

        fun canFactoryBuild(): Boolean {
            var barracksCount = 0
            var factoryCount = 0
            this.unitHeap.forEach { unit ->
                if (this@BHumanHeadquarters.owner!!.owns(unit)) {
                    when (unit) {
                        is BHumanBarracks -> barracksCount++
                        is BHumanFactory -> factoryCount++
                    }
                }
            }
            return barracksCount - factoryCount > 0
        }

        fun canGeneratorBuild(): Boolean {
            var generatorCount = 0
            for (unit in this.unitHeap) {
                if (unit is BHumanGenerator && this@BHumanHeadquarters.owner!!.owns(unit)) {
                    if (generatorCount == GENERATOR_LIMIT) {
                        return false
                    } else {
                        generatorCount++
                    }
                }
            }
            return true
        }

        fun canUpgrade(): Boolean {
            for (unit in this.unitHeap) {
                if (
                    unit is BHumanBuilding
                    && unit is BLevelable
                    && unit.currentLevel < unit.maxLevel
                    && this@BHumanHeadquarters.owner!!.owns(unit)
                ) {
                    return true
                }
            }
            return false
        }
    }
}