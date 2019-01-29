package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.scenario.plugin.implementation.race.BUiRacePlugin
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.trigger.building.*
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.trigger.infantry.BUiSkirmishHumanMarineOnCreateTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.trigger.vehicle.BUiSkirmishHumanTankOnCreateTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.barracks.builder.BUiSkirmishHumanBarracksBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.factory.builder.BUiSkirmishHumanFactoryBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.generator.builder.BUiSkirmishHumanGeneratorBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.headquarters.builder.BUiSkirmishHumanHeadquartersBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.turret.builder.BUiSkirmishHumanTurretBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.wall.builder.BUiSkirmishHumanWallBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.infantry.builder.BUiSkirmishHumanMarineBuilder
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.vehicle.builder.BUiSkirmishHumanTankBuilder
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.building.implementation.*
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.infantry.implementation.BHumanMarine
import com.orego.battlecrane.bc.engine.standardImpl.race.human.unit.vehicle.implementation.BHumanTank

class BUiSkirmishHumanPlugin(playerId: Long) : BUiRacePlugin(playerId) {

    override fun install(uiGameContext: BUiGameContext) {
        super.install(uiGameContext)
        //Connect building creators:
        BUiSkirmishHumanBarracksOnCreateTrigger.connect(uiGameContext, this.playerId)
        BUiSkirmishHumanFactoryOnCreateTrigger.connect(uiGameContext, this.playerId)
        BUiSkirmishHumanGeneratorOnCreateTrigger.connect(uiGameContext, this.playerId)
        BUiSkirmishHumanTurretOnCreateTrigger.connect(uiGameContext, this.playerId)
        BUiSkirmishHumanWallOnCreateTrigger.connect(uiGameContext, this.playerId)
        //Connect infantry creators:
        BUiSkirmishHumanMarineOnCreateTrigger.connect(uiGameContext, this.playerId)
        //Connect vehicle creators:
        BUiSkirmishHumanTankOnCreateTrigger.connect(uiGameContext, this.playerId)
    }

    override val uiUnitBuilderMap: Map<Class<out BUnit>, BUiUnit.Builder> = mapOf(
        //Building:
        BHumanBarracks::class.java to BUiSkirmishHumanBarracksBuilder(),
        BHumanFactory::class.java to BUiSkirmishHumanFactoryBuilder(),
        BHumanGenerator::class.java to BUiSkirmishHumanGeneratorBuilder(),
        BHumanHeadquarters::class.java to BUiSkirmishHumanHeadquartersBuilder(),
        BHumanTurret::class.java to BUiSkirmishHumanTurretBuilder(),
        BHumanWall::class.java to BUiSkirmishHumanWallBuilder(),
        //Creature:
        BHumanMarine::class.java to BUiSkirmishHumanMarineBuilder(),
        //Vehicle:
        BHumanTank::class.java to BUiSkirmishHumanTankBuilder()
    )
}