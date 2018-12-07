package com.orego.battlecrane.ui.model.std.view.race.human

import com.orego.battlecrane.ui.model.std.view.race.human.map.building.*
import com.orego.battlecrane.ui.model.std.view.race.human.map.infantry.BHumanMarineView
import com.orego.battlecrane.ui.model.std.view.race.human.map.vehicle.BHumanTankView
import com.orego.battlecrane.ui.model.std.view.race.human.tool.building.*
import com.orego.battlecrane.ui.model.std.view.race.human.tool.infantry.BHumanTrainMarineLvl1View
import com.orego.battlecrane.ui.model.std.view.race.human.tool.infantry.BHumanTrainMarineLvl2View
import com.orego.battlecrane.ui.model.std.view.race.human.tool.infantry.BHumanTrainMarineLvl3View
import com.orego.battlecrane.ui.model.std.view.race.human.tool.vehicle.BHumanTrainTankLvl1View
import com.orego.battlecrane.ui.model.std.view.race.human.tool.vehicle.BHumanTrainTankLvl2View
import com.orego.battlecrane.ui.model.std.view.race.human.tool.vehicle.BHumanTrainTankLvl3View

class BHumanViewBuilders {

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
        BHumanTankView.Builder()
    )
    val buildingTools = setOf(
        BHumanBuildBarracksView.Builder(),
        BHumanBuildFactoryView.Builder(),
        BHumanBuildGeneratorView.Builder(),
        BHumanBuildTurretView.Builder(),
        BHumanBuildWallView.Builder(),
        BHumanUpgradeBuildingView.Builder()
    )

    val armyTools = setOf(
        BHumanTrainMarineLvl1View.Builder(),
        BHumanTrainMarineLvl2View.Builder(),
        BHumanTrainMarineLvl3View.Builder(),
        BHumanTrainTankLvl1View.Builder(),
        BHumanTrainTankLvl2View.Builder(),
        BHumanTrainTankLvl3View.Builder()
    )

    //TODO: LATER:
//    val bonusTools = setOf(
//
//    )
}