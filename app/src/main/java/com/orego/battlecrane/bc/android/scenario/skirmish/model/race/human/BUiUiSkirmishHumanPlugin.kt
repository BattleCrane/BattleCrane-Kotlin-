package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human

import com.orego.battlecrane.bc.android.api.model.unit.BUnitHolder
import com.orego.battlecrane.bc.android.api.scenario.plugin.BUiRacePlugin
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.adjutant.BSkirmishHumanAdjutantHolderBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.barracks.builder.BSkirmishHumanBarracksHolderBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.factory.builder.BSkirmishHumanFactoryHolderBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.generator.builder.BSkirmishHumanGeneratorHolderBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.headquarters.builder.BSkirmishHumanHeadquartersHolderBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.turret.builder.BSkirmishHumanTurretHolderBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.wall.builder.BSkirmishHumanWallHolderBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.infantry.builder.BSkirmishHumanMarineHolderBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.vehicle.builder.BSkirmishHumanTankHolderBuilder
import com.orego.battlecrane.bc.android.standardImpl.race.human.adjutant.BHumanAdjutantHolder
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.*
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.infantry.implementation.BHumanMarine
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.vehicle.implementation.BHumanTank

class BUiUiSkirmishHumanPlugin : BUiRacePlugin() {

    override val uiAdjutantBuilderPair: Pair<Class<BHumanAdjutant>, BHumanAdjutantHolder.Builder> =
        BHumanAdjutant::class.java to BSkirmishHumanAdjutantHolderBuilder()

    override val uiUnitBuilderMap: Map<Class<out BUnit>, BUnitHolder.Builder> = mapOf(
        //Building:
        BHumanBarracks::class.java to BSkirmishHumanBarracksHolderBuilder(),
        BHumanFactory::class.java to BSkirmishHumanFactoryHolderBuilder(),
        BHumanGenerator::class.java to BSkirmishHumanGeneratorHolderBuilder(),
        BHumanHeadquarters::class.java to BSkirmishHumanHeadquartersHolderBuilder(),
        BHumanTurret::class.java to BSkirmishHumanTurretHolderBuilder(),
        BHumanWall::class.java to BSkirmishHumanWallHolderBuilder(),
        //Creature:
        BHumanMarine::class.java to BSkirmishHumanMarineHolderBuilder(),
        //Vehicle:
        BHumanTank::class.java to BSkirmishHumanTankHolderBuilder()
    )
}