package com.orego.battlecrane.bc.std.race.human.scenario.skirmish.adjutant

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.manager.mechanics.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.contract.BProducable
import com.orego.battlecrane.bc.std.race.human.action.build.*
import com.orego.battlecrane.bc.std.race.human.action.train.*
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanBarracks
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanFactory
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanGenerator

class BHumanAdjutant(
    context: BGameContext,
    owner: BPlayer,
    bonusFactories: MutableSet<BAction.Factory>
) : BAdjutant(context, owner, bonusFactories) {

    companion object {

        private const val HEADQUARTERS_BUILD_ABILITY = 1

        private const val GENERATOR_LIMIT = 2
    }

    private val unitHeap by lazy {
        this.context.mapManager.unitHeap.values
    }

    private val alertManager = AlertManager()

    private val resourceManager = ResourceManager()

    override fun onGameStarted() {
    }

    override fun onTurnStarted() {
        this.alertManager.activateUnits()
        this.resourceManager.loadResources()
    }

    override fun onTurnEnded() {
    }

    inner class AlertManager {

        private val unitHeap by lazy {
            this@BHumanAdjutant.unitHeap
        }

        fun activateUnits() {
            this.activateFactories()
            this.activateAttackers()
        }

        private fun activateAttackers() {
            //TODO:
        }

        private fun activateFactories() {
            this.unitHeap
                .filter { unit -> this@BHumanAdjutant.owner.owns(unit) && unit is BProducable }
                .map { unit -> unit as BProducable }
                .forEach { unit -> unit.isReadyToProduce = true }
        }
    }

    inner class ResourceManager : BAdjutant.ResourceManager() {

        private val unitHeap by lazy {
            this@BHumanAdjutant.unitHeap
        }

        val calculator = Calculator()

        val resourceSupplier = ResourceSupplier()

        private var buildingActionCount = 0

        private var factoryActionCount = 0

        private var generatorActionCount = 0

        private var trainMarineLvl1ActionCount = 0

        private var trainMarineLvl2ActionCount = 0

        private var trainMarineLvl3ActionCount = 0

        private var trainTankLvl1ActionCount = 0

        private var trainTankLvl2ActionCount = 0

        private var trainTankLvl3ActionCount = 0

        fun loadResources() {
            this.calcResources()
            this.supplyResources()
        }

        private fun calcResources() {
            this.influenceCount++
            this.buildingActionCount = this.calculator.calcBuildingActionCount()
            if (this.buildingActionCount > 0) {
                this.factoryActionCount = this.calculator.calcFactoryActionCount()
                this.generatorActionCount = this.calculator.calcGeneratorActionCount()
            }
            this.trainMarineLvl1ActionCount = this.calculator.calcTrainMarineLvl1ActionCount()
            if (this.trainMarineLvl1ActionCount > 0) {
                this.trainMarineLvl2ActionCount = this.calculator.calcTrainMarineLvl2ActionCount()
                if (this.trainMarineLvl2ActionCount > 0) {
                    this.trainMarineLvl3ActionCount = this.calculator.calcTrainMarineLvl3ActionCount()
                }
            }
            this.trainTankLvl1ActionCount = this.calculator.calcTrainTankLvl1ActionCount()
            if (this.trainTankLvl1ActionCount > 0) {
                this.trainTankLvl2ActionCount = this.calculator.calcTrainTankLvl2ActionCount()
                if (this.trainTankLvl2ActionCount > 0) {
                    this.trainTankLvl3ActionCount = this.calculator.calcTrainTankLvl3ActionCount()
                }
            }
        }

        private fun supplyResources() {
            this.resourceSupplier.suppy()
        }

        /**
         * Calculator.
         */

        inner class Calculator {

            fun calcBuildingActionCount(): Int {
                val owner = this@BHumanAdjutant.owner
                var abilityCount = HEADQUARTERS_BUILD_ABILITY
                this@ResourceManager.unitHeap.forEach { unit ->
                    if (owner.owns(unit) && unit is BHumanGenerator && unit.isReadyToProduce) {
                        abilityCount++
                    }
                }
                return abilityCount
            }

            fun calcFactoryActionCount(): Int {
                var barracksCount = 0
                var factoryCount = 0
                this@ResourceManager.unitHeap.forEach { unit ->
                    if (this@BHumanAdjutant.owner.owns(unit)) {
                        when (unit) {
                            is BHumanBarracks -> barracksCount++
                            is BHumanFactory -> factoryCount++
                        }
                    }
                }
                val comparison = barracksCount - factoryCount
                return if (comparison > 0) {
                    comparison
                } else {
                    0
                }
            }

            fun calcGeneratorActionCount(): Int {
                var generatorCount = 0
                this@ResourceManager.unitHeap.forEach { unit ->
                    if (this@BHumanAdjutant.owner.owns(unit)
                        && unit is BHumanGenerator
                        && unit.isReadyToProduce) {
                        if (generatorCount == GENERATOR_LIMIT) {
                            return@forEach
                        } else {
                            generatorCount++
                        }
                    }
                }
                return generatorCount
            }

            fun calcTrainMarineLvl1ActionCount(): Int {
                val owner = this@BHumanAdjutant.owner
                var abilityCount = 0
                this@ResourceManager.unitHeap.forEach { unit ->
                    if (owner.owns(unit)
                        && unit is BHumanBarracks
                        && unit.isReadyToProduce
                    ) {
                        abilityCount++
                    }
                }
                return abilityCount
            }

            fun calcTrainMarineLvl2ActionCount(): Int {
                val owner = this@BHumanAdjutant.owner
                var abilityCount = 0
                this@ResourceManager.unitHeap.forEach { unit ->
                    if (owner.owns(unit)
                        && unit is BHumanBarracks
                        && unit.currentLevel > 1
                        && unit.isReadyToProduce
                    ) {
                        abilityCount++
                    }
                }
                return abilityCount
            }

            fun calcTrainMarineLvl3ActionCount(): Int {
                val owner = this@BHumanAdjutant.owner
                var abilityCount = 0
                this@ResourceManager.unitHeap.forEach { unit ->
                    if (owner.owns(unit)
                        && unit is BHumanBarracks
                        && unit.currentLevel > 2
                        && unit.isReadyToProduce
                    ) {
                        abilityCount++
                    }
                }
                return abilityCount
            }

            fun calcTrainTankLvl1ActionCount(): Int {
                val owner = this@BHumanAdjutant.owner
                var abilityCount = 0
                this@ResourceManager.unitHeap.forEach { unit ->
                    if (owner.owns(unit) && unit is BHumanFactory) {
                        abilityCount++
                    }
                }
                return abilityCount
            }

            fun calcTrainTankLvl2ActionCount(): Int {
                val owner = this@BHumanAdjutant.owner
                var abilityCount = 0
                this@ResourceManager.unitHeap.forEach { unit ->
                    if (owner.owns(unit)
                        && unit is BHumanFactory
                        && unit.currentLevel > 1
                    ) {
                        abilityCount++
                    }
                }
                return abilityCount
            }

            fun calcTrainTankLvl3ActionCount(): Int {
                val owner = this@BHumanAdjutant.owner
                var abilityCount = 0
                this@ResourceManager.unitHeap.forEach { unit ->
                    if (owner.owns(unit)
                        && unit is BHumanFactory
                        && unit.currentLevel > 2
                    ) {
                        abilityCount++
                    }
                }
                return abilityCount
            }
        }

        inner class ResourceSupplier {

            fun suppy() {
                this.supplyBuildings()
                this.supplyArmy()
            }

            private fun supplyBuildings() {
                val context = this@BHumanAdjutant.context
                val resourceManager = this@ResourceManager
                val owner = this@BHumanAdjutant.owner
                if (resourceManager.buildingActionCount > 0) {
                    val buildingActions = resourceManager.buildingActions
                    buildingActions.addAll(
                        setOf(
                            BHumanBuildBarracks(context, owner),
                            BHumanBuildTurret(context, owner),
                            BHumanBuildWall(context, owner),
                            BHumanUpgradeBuilding(context, owner)
                        )
                    )
                    if (resourceManager.factoryActionCount > 0) {
                        buildingActions.add(BHumanBuildFactory(context, owner))
                    }
                    if (resourceManager.generatorActionCount > 0) {
                        buildingActions.add(BHumanBuildGenerator(context, owner))
                    }
                }
            }

            private fun supplyArmy() {
                val context = this@BHumanAdjutant.context
                val resourceManager = this@ResourceManager
                val owner = this@BHumanAdjutant.owner
                val armyActions = resourceManager.buildingActions
                if (resourceManager.trainMarineLvl1ActionCount > 0) {
                    armyActions.add(BHumanTrainMarineLvl1(context, owner))
                }
                if (resourceManager.trainMarineLvl2ActionCount > 0) {
                    armyActions.add(BHumanTrainMarineLvl2(context, owner))
                }
                if (resourceManager.trainMarineLvl3ActionCount > 0) {
                    armyActions.add(BHumanTrainMarineLvl3(context, owner))
                }
                if (resourceManager.trainTankLvl1ActionCount > 0) {
                    armyActions.add(BHumanTrainTankLvl1(context, owner))
                }
                if (resourceManager.trainTankLvl2ActionCount > 0) {
                    armyActions.add(BHumanTrainTankLvl2(context, owner))
                }
                if (resourceManager.trainTankLvl3ActionCount > 0) {
                    armyActions.add(BHumanTrainTankLvl3(context, owner))
                }
            }
        }
    }
}