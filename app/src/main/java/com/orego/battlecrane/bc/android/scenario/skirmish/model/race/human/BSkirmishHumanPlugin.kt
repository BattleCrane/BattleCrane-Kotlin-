package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human

import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.adjutant.BHumanAdjutant
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.*
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.infantry.implementation.BHumanMarine
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.vehicle.implementation.BHumanTank
import com.orego.battlecrane.bc.android.api.holder.unit.BUnitHolder
import com.orego.battlecrane.bc.android.api.scenario.plugin.BRacePlugin
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.adjutant.BSkirmishHumanAdjutantHolderBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.headquarters.builder.BSkirmishHumanHeadquartersHolderBuilder
import com.orego.battlecrane.bc.android.standardImpl.race.human.adjutant.BHumanAdjutantHolder
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.*
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.infantry.BHumanMarineHolder
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.vehicle.BHumanTankHolder

class BSkirmishHumanPlugin : BRacePlugin() {

    override val uiAdjutantBuilderPair: Pair<Class<BHumanAdjutant>, BHumanAdjutantHolder.Builder> =
        BHumanAdjutant::class.java to BSkirmishHumanAdjutantHolderBuilder()

    override val uiUnitBuilderMap: Map<Class<out BUnit>, BUnitHolder.Builder> = mapOf(
        //Building:
        BHumanBarracks::class.java to BHumanBarracksHolder.Builder(),
        BHumanFactory::class.java to BHumanFactoryHolder.Builder(),
        BHumanGenerator::class.java to BHumanGeneratorHolder.Builder(),
        BHumanHeadquarters::class.java to BSkirmishHumanHeadquartersHolderBuilder(),
        BHumanTurret::class.java to BHumanTurretHolder.Builder(),
        BHumanWall::class.java to BHumanWallHolder.Builder(),
        //Creature:
        BHumanMarine::class.java to BHumanMarineHolder.Builder(),
        //Vehicle:
        BHumanTank::class.java to BHumanTankHolder.Builder()
    )
}