package com.orego.battlecrane.bc.std.race.human.scenario.skirmish.adjutant

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.std.race.human.action.build.*
import com.orego.battlecrane.bc.std.race.human.action.train.*
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanBarracks
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanFactory
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanGenerator

class BHumanAdjutant(
    context: BGameContext,
    owner: BPlayer,
    bonusProducers: MutableSet<BAction.Producer>
) : BAdjutant() {

    override val assistants = mutableSetOf(
        BuildingAssistant(context, owner),
        InfantryAssistantLvl1(context, owner),
        InfantryAssistantLvl2(context, owner),
        InfantryAssistantLvl3(context, owner),
        VehicleAssistantLvl1(context, owner),
        VehicleAssistantLvl2(context, owner),
        VehicleAssistantLvl3(context, owner),
        BonusAssistant(owner, bonusProducers)
    )

    /**
     * Building assistant.
     */

    class BuildingAssistant(context: BGameContext, private val owner: BPlayer) : BAssistant() {

        companion object {

            private const val HEADQUARTERS_BUILD_ABILITY = 1
        }

        private val unityHeap by lazy {
            context.mapManager.unitHeap.values
        }

        override val producerSet = mutableSetOf(
            BHumanBuildBarracks.Producer(context, this.owner),
            BHumanBuildGenerator.Producer(context, this.owner),
            BHumanBuildFactory.Producer(context, this.owner),
            BHumanBuildWall.Producer(context, this.owner),
            BHumanBuildTurret.Producer(context, this.owner),
            BHumanUpgradeBuilding.Producer(context, this.owner)
        )

        override fun calcAbilityCount(): Int {
            val owner = this.owner
            var abilityCount = HEADQUARTERS_BUILD_ABILITY
            this.unityHeap.forEach { unit ->
                if (owner.owns(unit) && unit is BHumanGenerator) {
                    abilityCount++
                }
            }
            return abilityCount
        }
    }

    /**
     * Infantry assistants.
     */

    class InfantryAssistantLvl1(context: BGameContext, private val owner: BPlayer) : BAssistant() {

        private val unityHeap by lazy {
            context.mapManager.unitHeap.values
        }

        override val producerSet = mutableSetOf<BAction.Producer>(BHumanTrainMarineLvl1.Producer(context, this.owner))

        override fun calcAbilityCount(): Int {
            val owner = this.owner
            var abilityCount = 0
            this.unityHeap.forEach { unit ->
                if (owner.owns(unit) && unit is BHumanBarracks) {
                    abilityCount++
                }
            }
            return abilityCount
        }
    }


    class InfantryAssistantLvl2(context: BGameContext, private val owner: BPlayer) : BAssistant() {

        private val unityHeap by lazy {
            context.mapManager.unitHeap.values
        }

        override val producerSet = mutableSetOf<BAction.Producer>(BHumanTrainMarineLvl2.Producer(context, this.owner))

        override fun calcAbilityCount(): Int {
            val owner = this.owner
            var abilityCount = 0
            this.unityHeap.forEach { unit ->
                if (owner.owns(unit) && unit is BHumanBarracks && unit.currentLevel > 1) {
                    abilityCount++
                }
            }
            return abilityCount
        }
    }


    class InfantryAssistantLvl3(context: BGameContext, private val owner: BPlayer) : BAssistant() {

        private val unityHeap by lazy {
            context.mapManager.unitHeap.values
        }

        override val producerSet = mutableSetOf<BAction.Producer>(BHumanTrainMarineLvl3.Producer(context, this.owner))

        override fun calcAbilityCount(): Int {
            val owner = this.owner
            var abilityCount = 0
            this.unityHeap.forEach { unit ->
                if (owner.owns(unit) && unit is BHumanBarracks && unit.currentLevel > 2) {
                    abilityCount++
                }
            }
            return abilityCount
        }
    }


    /**
     * Vehicle assistants.
     */

    class VehicleAssistantLvl1(context: BGameContext, private val owner: BPlayer) : BAssistant() {

        private val unityHeap by lazy {
            context.mapManager.unitHeap.values
        }

        override val producerSet = mutableSetOf<BAction.Producer>(BHumanTrainTankLvl1.Producer(context, this.owner))

        override fun calcAbilityCount(): Int {
            val owner = this.owner
            var abilityCount = 0
            this.unityHeap.forEach { unit ->
                if (owner.owns(unit) && unit is BHumanFactory) {
                    abilityCount++
                }
            }
            return abilityCount
        }
    }


    class VehicleAssistantLvl2(context: BGameContext, private val owner: BPlayer) : BAssistant() {

        private val unityHeap by lazy {
            context.mapManager.unitHeap.values
        }

        override val producerSet = mutableSetOf<BAction.Producer>(BHumanTrainTankLvl2.Producer(context, this.owner))

        override fun calcAbilityCount(): Int {
            val owner = this.owner
            var abilityCount = 0
            this.unityHeap.forEach { unit ->
                if (owner.owns(unit) && unit is BHumanFactory && unit.currentLevel > 1) {
                    abilityCount++
                }
            }
            return abilityCount
        }
    }


    class VehicleAssistantLvl3(context: BGameContext, private val owner: BPlayer) : BAssistant() {

        private val unityHeap by lazy {
            context.mapManager.unitHeap.values
        }

        override val producerSet = mutableSetOf<BAction.Producer>(BHumanTrainTankLvl3.Producer(context, this.owner))

        override fun calcAbilityCount(): Int {
            val owner = this.owner
            var abilityCount = 0
            this.unityHeap.forEach { unit ->
                if (owner.owns(unit) && unit is BHumanFactory && unit.currentLevel > 2) {
                    abilityCount++
                }
            }
            return abilityCount
        }
    }

    /**
     * Bonus assistant.
     */

    class BonusAssistant(private val owner: BPlayer, producers: MutableSet<BAction.Producer>) :
        BAssistant() {

        override val producerSet = producers

        override fun calcAbilityCount() = this.owner.adjutant.influenceResourceCount
    }
}