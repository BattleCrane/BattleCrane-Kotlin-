package com.orego.battlecrane.ui.model.std.view.race.human

import com.orego.battlecrane.ui.model.std.view.race.human.map.building.*
import com.orego.battlecrane.ui.model.std.view.race.human.map.infantry.BHumanMarineView
import com.orego.battlecrane.ui.model.std.view.race.human.tool.building.*
import com.orego.battlecrane.ui.model.std.view.race.human.tool.infantry.BHumanMarine1ToolView
import com.orego.battlecrane.ui.model.std.view.race.human.tool.infantry.BHumanMarine2ToolView
import com.orego.battlecrane.ui.model.std.view.race.human.tool.infantry.BHumanMarine3ToolView
import com.orego.battlecrane.ui.model.std.view.race.human.tool.vehicle.BHumanTank1ToolView
import com.orego.battlecrane.ui.model.std.view.race.human.tool.vehicle.BHumanTank2ToolView
import com.orego.battlecrane.ui.model.std.view.race.human.tool.vehicle.BHumanTank3ToolView

object BHumanViewBuilders {

    val units = setOf(
        //Buildings:
        BHumanBarracksView.Builder(),
        BHumanFactoryView.Builder(),
        BHumanGeneratorView.Builder(),
        BHumanHeadquartersView.Builder(),
        BHumanTurretView.Builder(),
        BHumanWallView.Builder(),
        //Army:
        BHumanMarineView.Builder(),
        BHumanTurretView.Builder()
    )
    val buildingTools = setOf(
        BHumanBarracksToolView.Builder(),
        BHumanFactoryToolView.Builder(),
        BHumanGeneratorToolView.Builder(),
        BHumanTurretToolView.Builder(),
        BHumanWallToolView.Builder(),
        BHumanUpgradeToolView.Builder()
    )

    val armyTools = setOf(
        BHumanMarine1ToolView.Builder(),
        BHumanMarine2ToolView.Builder(),
        BHumanMarine3ToolView.Builder(),
        BHumanTank1ToolView.Builder(),
        BHumanTank2ToolView.Builder(),
        BHumanTank3ToolView.Builder()
    )

    //TODO: LATER:
//    val bonusTools = setOf(
//
//    )
}