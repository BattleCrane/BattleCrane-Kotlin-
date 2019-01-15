package com.orego.battlecrane.ui.model.std.race.human

import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.adjutant.BHumanAdjutant
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanBarracks
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanFactory
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanGenerator
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanHeadquarters
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanTurret
import com.orego.battlecrane.bc.std.race.human.unit.building.implementation.BHumanWall
import com.orego.battlecrane.bc.std.race.human.unit.infantry.implementation.marine.BHumanMarine
import com.orego.battlecrane.bc.std.race.human.unit.vehicle.implementation.tank.BHumanTank
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