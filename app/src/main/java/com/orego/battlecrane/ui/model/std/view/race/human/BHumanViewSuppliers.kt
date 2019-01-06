package com.orego.battlecrane.ui.model.std.view.race.human

import com.orego.battlecrane.bc.std.race.human.adjutant.BHumanAdjutant
import com.orego.battlecrane.ui.model.std.view.race.human.action.building.*
import com.orego.battlecrane.ui.model.std.view.race.human.action.infantry.BHumanTrainMarineLvl1View
import com.orego.battlecrane.ui.model.std.view.race.human.action.infantry.BHumanTrainMarineLvl2View
import com.orego.battlecrane.ui.model.std.view.race.human.action.infantry.BHumanTrainMarineLvl3View
import com.orego.battlecrane.ui.model.std.view.race.human.action.vehicle.BHumanTrainTankLvl1View
import com.orego.battlecrane.ui.model.std.view.race.human.action.vehicle.BHumanTrainTankLvl2View
import com.orego.battlecrane.ui.model.std.view.race.human.action.vehicle.BHumanTrainTankLvl3View
import com.orego.battlecrane.ui.model.std.view.race.human.unit.building.*
import com.orego.battlecrane.ui.model.std.view.race.human.unit.infantry.BHumanMarineView
import com.orego.battlecrane.ui.model.std.view.race.human.unit.vehicle.BHumanTankView

class BHumanViewSuppliers {

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

    val adjutant = BHumanAdjutantView.Builder()
}