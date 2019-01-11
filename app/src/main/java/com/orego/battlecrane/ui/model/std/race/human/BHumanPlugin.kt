package com.orego.battlecrane.ui.model.std.race.human

import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.adjutant.BHumanAdjutant
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.*
import com.orego.battlecrane.bc.std.race.human.unit.infantry.implementation.BHumanMarine
import com.orego.battlecrane.bc.std.race.human.unit.vehicle.implementation.BHumanTank
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder
import com.orego.battlecrane.ui.model.api.plugin.BRacePlugin
import com.orego.battlecrane.ui.model.std.race.human.adjutant.BHumanAdjutantHolder
import com.orego.battlecrane.ui.model.std.race.human.unit.building.*
import com.orego.battlecrane.ui.model.std.race.human.unit.infantry.BHumanMarineHolder

class BHumanPlugin : BRacePlugin() {

    override val uiAdjutantBuilderPair: Pair<Class<BHumanAdjutant>, BHumanAdjutantHolder.Builder> =
        BHumanAdjutant::class.java to BHumanAdjutantHolder.Builder()

    override val uiUnitBuilderMap: Map<Class<out BUnit>, BUnitHolder.Builder> = mapOf(
        //Building:
        BHumanBarracks::class.java to BHumanBarracksHolder.Builder(),
        BHumanFactory::class.java to BHumanFactoryHolder.Builder(),
        BHumanGenerator::class.java to BHumanGeneratorHolder.Builder(),
        BHumanHeadquarters::class.java to BHumanHeadquartersHolder.Builder(),
        BHumanTurret::class.java to BHumanTurretHolder.Builder(),
        BHumanWall::class.java to BHumanWallHolder.Builder(),
        //Creature:
        BHumanMarine::class.java to BHumanMarineHolder.Builder(),
        //Vehicle:
        BHumanTank::class.java to BHumanTurretHolder.Builder()
    )
}