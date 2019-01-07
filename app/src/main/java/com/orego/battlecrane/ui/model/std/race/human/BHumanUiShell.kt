package com.orego.battlecrane.ui.model.std.race.human

import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.ui.model.api.shell.BUiRaceShell
import com.orego.battlecrane.ui.model.api.shell.item.supplier.BUiItemSupplier
import com.orego.battlecrane.ui.model.std.race.human.unit.building.*
import com.orego.battlecrane.ui.model.std.race.human.unit.infantry.BHumanMarineView
import com.orego.battlecrane.ui.model.std.race.human.unit.vehicle.BHumanTankView

class BHumanUiShell : BUiRaceShell {


    override val adjutantSupplier: BUiItemSupplier<BAdjutant> = BHumanAdjutantView.Builder()

    override val unitSupplierSet: Set<BUiItemSupplier<BUnit>> = setOf(
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
}