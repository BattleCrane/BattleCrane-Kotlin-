package com.orego.battlecrane.bc.std.race.human.unit.building.implementation

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.player.BPlayer
import com.orego.battlecrane.bc.api.model.entity.property.BHitPointable
import com.orego.battlecrane.bc.api.model.entity.property.BLevelable
import com.orego.battlecrane.bc.api.model.entity.property.BProducable
import com.orego.battlecrane.bc.std.race.human.unit.building.BHumanBuilding

class BHumanHeadquarters(context: BGameContext, playerId: Long, x : Int, y : Int)
    : BHumanBuilding(context, playerId, x, y), BHitPointable, BLevelable, BProducable {

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

    override val height = DEFAULT_VERTICAL_SIDE

    override val width = DEFAULT_HORIZONTAL_SIDE

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

    override fun pushProduceActions(context: BGameContext, owner: BPlayer) = mutableSetOf<BAction>()
        .also { set ->
            if (this.isProduceEnable) {
                this.buildBarracksFactory.sendOnCreateUnitAction()?.let { set.add(it) }
                this.buildTurretFactory.sendOnCreateUnitAction()?.let { set.add(it) }
                this.buildWallFactory.sendOnCreateUnitAction()?.let { set.add(it) }
                if (this.buildDeveloper.canFactoryBuild()) {
                    this.buildFactoryFactory.sendOnCreateUnitAction()?.let { set.add(it) }
                }
                if (this.buildDeveloper.canGeneratorBuild()) {
                    this.buildGeneratorFactory.sendOnCreateUnitAction()?.let { set.add(it) }
                }
                if (this.buildDeveloper.canUpgrade()) {
                    this.upgrageBuildingFactory.sendOnCreateUnitAction()?.let { set.add(it) }
                }
            }
        }

    /**
     * Actions.
     */

    abstract inner class Build : BHumanAction(this.context, this.playerId!!),
        BTargetable {

        override var targetPosition: BPoint? = null

        protected abstract fun buildUnit(): BHumanBuilding

        override fun performAction(): Boolean {
            if (this.targetPosition != null && this.ownerId != null) {
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

    inner class BuildBarracksFactory : BAction.Factory(this.pipeline) {

        override fun createAction() = Action()

        inner class Action : Build() {

            override fun buildUnit() =
                BHumanBarracks(this@BHumanHeadquarters.context, this@BHumanHeadquarters.playerId!!)
        }
    }

    inner class BuildTurretFactory : BAction.Factory(this.pipeline) {

        override fun createAction() =  Action()

        inner class Action : Build() {

            override fun buildUnit() =
                BHumanTurret(this@BHumanHeadquarters.context, this@BHumanHeadquarters.playerId!!)
        }
    }

    inner class BuildWallFactory : BAction.Factory(this.pipeline) {

        override fun createAction() = Action()

        inner class Action : Build() {

            override fun buildUnit() =
                BHumanWall(this@BHumanHeadquarters.context, this@BHumanHeadquarters.playerId!!)
        }
    }

    inner class BuildFactoryFactory : BAction.Factory(this.pipeline) {

        override fun createAction() = Action()

        inner class Action : Build() {

            override fun buildUnit() =
                BHumanFactory(this@BHumanHeadquarters.context, this@BHumanHeadquarters.playerId!!)
        }
    }

    inner class BuildGeneratorFactory : BAction.Factory(this.pipeline) {

        override fun createAction() =  Action()

        inner class Action : Build() {

            override fun buildUnit() =
                BHumanGenerator(this@BHumanHeadquarters.context, this@BHumanHeadquarters.playerId!!)
        }
    }

    inner class UpgrageBuildingFactory : BAction.Factory(this.pipeline) {

        override fun createAction() = Action()

        inner class Action : BHumanAction(
            this@BHumanHeadquarters.context, this@BHumanHeadquarters.playerId!!
        ), BTargetable {

            override var targetPosition: BPoint? = null

            override fun performAction(): Boolean {
                if (this.targetPosition != null && this.ownerId != null) {
                    val manager = this.context.mapManager
                    val unit = manager.getUnitByPosition(this.targetPosition)
                    if (unit is BLevelable && unit is BHumanBuilding) {
                        return unit.increaseLevel(DEFAULT_BUILDING_UPGRADE)
                    }
                }
                return false
            }
        }
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
            val owner = this@BHumanHeadquarters.playerId!!
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
                if (this@BHumanHeadquarters.playerId!!.owns(unit)) {
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
                if (unit is BHumanGenerator && this@BHumanHeadquarters.playerId!!.owns(unit)) {
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
                    && this@BHumanHeadquarters.playerId!!.owns(unit)
                ) {
                    return true
                }
            }
            return false
        }
    }
}