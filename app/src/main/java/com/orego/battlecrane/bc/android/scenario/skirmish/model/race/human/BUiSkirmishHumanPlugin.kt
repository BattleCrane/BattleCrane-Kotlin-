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

    override val renderFunctionMap: Map<Class<out BUnit>, (BUiGameContext, BUnit) -> BUiUnit> = mapOf(
        //Building:
        BHumanBarracks::class.java to { context, unit ->
            BUiSkirmishHumanBarracksBuilder(unit as BHumanBarracks).build(context)
        },
        BHumanFactory::class.java to { context, unit ->
            BUiSkirmishHumanFactoryBuilder(unit as BHumanFactory).build(context)
        },
        BHumanGenerator::class.java to { context, unit ->
            BUiSkirmishHumanGeneratorBuilder(unit as BHumanGenerator).build(context)
        },
        BHumanHeadquarters::class.java to { context, unit ->
            BUiSkirmishHumanHeadquartersBuilder(unit as BHumanHeadquarters).build(context)
        },
        BHumanTurret::class.java to { context, unit ->
            BUiSkirmishHumanTurretBuilder(unit as BHumanTurret).build(context)
        },
        BHumanWall::class.java to { context, unit ->
            BUiSkirmishHumanWallBuilder(unit as BHumanWall).build(context)
        },
        //Creature:
        BHumanMarine::class.java to { context, unit ->
            BUiSkirmishHumanMarineBuilder(unit as BHumanMarine).build(context)
        },
        //Vehicle:
        BHumanTank::class.java to { context, unit ->
            BUiSkirmishHumanTankBuilder(unit as BHumanTank).build(context)
        }
    )

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
}