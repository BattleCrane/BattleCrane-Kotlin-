package com.orego.battlecrane.bc.std.race.human.scenario.skirmish.adjutant

import com.orego.battlecrane.bc.api.manager.BGameContext
import com.orego.battlecrane.bc.api.manager.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.std.race.human.action.build.*
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanBarracks
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanFactory
import com.orego.battlecrane.bc.std.race.human.building.implementation.BHumanGenerator

class BSkirmishHumanAdjutant(context: BGameContext, owner: BPlayer) : BAdjutant(context, owner) {

    private val unityHeap by lazy {
        this.context.mapManager.unitHeap.values
    }

    companion object {

        private const val HEADQUARTERS_BUILD_ABILITY = 1

        private const val GENERATOR_LIMIT = 2

        private const val DEFAULT_GENERATOR_ABILITY = 1
    }

    override fun onStartTurn() {
        super.onStartTurn()
        this.calcBuildingAbilityCount()
    }

    private fun calcBuildingAbilityCount() {
        this.buildingAbilityCount = HEADQUARTERS_BUILD_ABILITY
        this.unityHeap.forEach { unit ->
            if (this.owner.owns(unit) && unit is BHumanGenerator) {
                this.buildingAbilityCount++
            }
        }
    }

    fun setBarracksAbility() {
        if (this.buildingAbilityCount > 0) {
            this.buildingStack.add(BHumanBuildBarracks(this.context, this.owner))
        }
    }

    fun setFactoryAbility() {
        var barracksCount = 0
        var factoryCount = 0
        this.unityHeap.forEach { unit ->
            if (this.owner.owns(unit)) {
                when (unit) {
                    is BHumanBarracks -> barracksCount++
                    is BHumanFactory -> factoryCount++
                }
            }
        }
        val abilityCount = barracksCount - factoryCount
        if (abilityCount > 0) {
            this.buildingStack.add(BHumanBuildFactory(this.context, this.owner) to abilityCount)
        }
    }

    private fun collectTurrets() {
        var abilityCount = HEADQUARTERS_BUILD_ABILITY
        this.unityHeap.forEach { unit ->
            if (this.owner.owns(unit) && unit is BHumanGenerator) {
                abilityCount++
            }
        }
        this.buildingStack.add(BHumanBuildTurret(this.context, this.owner) to abilityCount)
    }

    private fun collectWalls() {
        var abilityCount = HEADQUARTERS_BUILD_ABILITY
        this.unityHeap.forEach { unit ->
            if (this.owner.owns(unit) && unit is BHumanGenerator) {
                abilityCount++
            }
        }
        this.buildingStack.add(BHumanBuildWall(this.context, this.owner) to abilityCount)
    }

    private fun collectGenerator() {
        var generatorCount = 0
        this.unityHeap.forEach { unit ->
            if (this.owner.owns(unit) && unit is BHumanGenerator) {
                if (generatorCount == GENERATOR_LIMIT) {
                    return
                } else {
                    generatorCount++
                }
            }
        }
        this.buildingStack.add(BHumanBuildGenerator(this.context, this.owner) to generatorCount)
    }

    BHumanBuildGenerator(this.context, this.owner) to abilityCount
    )

    //TODO: MAKE INIT FUNCTION:
    mutableSetOf(

    ),

    mutableSetOf(),

    mutableSetOf()
}