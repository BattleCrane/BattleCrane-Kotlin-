package com.orego.battlecrane.ui.model.std.race.human

import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.bc.std.race.human.adjutant.BHumanAdjutant
import com.orego.battlecrane.ui.model.api.factory.BUiRaceShell
import com.orego.battlecrane.ui.model.api.factory.item.supplier.BUiItemSupplier
import com.orego.battlecrane.ui.model.api.holder.adjutant.BAdjutantHolder
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder
import com.orego.battlecrane.ui.model.std.race.human.unit.building.*
import com.orego.battlecrane.ui.model.std.race.human.unit.infantry.BHumanMarineView
import com.orego.battlecrane.ui.model.std.race.human.unit.vehicle.BHumanTankView
import com.orego.battlecrane.ui.viewModel.BScenarioViewModel

class BHumanHolderFactory : BScenarioViewModel.RaceFactory() {

    override val uiAdjutantBuilder = BHumanAdjutant::class.java to BHumanAdjutant.Builder()

    override val uiUnitBuilders: Map<Class<out BUnit>, BUnitHolder.Builder> = mapOf()

//
//    override val adjutantSupplier: BUiItemSupplier<BAdjutant> = BHumanAdjutantView.Builder()
//
//    override val unitSupplierSet: Set<BUiItemSupplier<BUnit>> = setOf(
//        //Buildings:
//        BHumanBarracksView.Builder(),
//        BHumanFactoryView.Builder(),
//        BHumanGeneratorView.Builder(),
//        BHumanHeadquartersView.Builder(),
//        BHumanTurretView.Builder(),
//        BHumanWallView.Builder(),
//        //Army:
//        BHumanMarineView.Builder(),
//        BHumanTankView.Builder()
//    )
}