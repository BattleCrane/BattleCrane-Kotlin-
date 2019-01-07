package com.orego.battlecrane.ui.model.api.shell

import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.ui.model.api.shell.item.supplier.BUiItemSupplier

interface BUiRaceShell {

    val unitSupplierSet : Set<BUiItemSupplier<BUnit>>

    val adjutantSupplier : BUiItemSupplier<BAdjutant>
}