package com.orego.battlecrane.ui.model.api.shell

import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.ui.model.api.shell.itemShell.supplier.BUiItemSupplier

interface BUiShell {

    val unitSupplierSet : Set<BUiItemSupplier<BUnit>>

    val adjutantSupplier : BUiItemSupplier<BAdjutant>
}