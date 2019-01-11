package com.orego.battlecrane.ui.model.std.location.grass

import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.bc.std.location.grass.field.destroyed.BDestroyedField
import com.orego.battlecrane.bc.std.location.grass.field.empty.BEmptyField
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder
import com.orego.battlecrane.ui.model.api.plugin.BLocationPlugin
import com.orego.battlecrane.ui.model.std.location.grass.field.destroyed.BDestroyedFieldHolder
import com.orego.battlecrane.ui.model.std.location.grass.field.empty.BEmptyFieldHolder

class BGrassLocationPlugin : BLocationPlugin() {

    override val uiUnitBuilderMap: Map<Class<out BUnit>, BUnitHolder.Builder> = mapOf(
        BEmptyField::class.java to BEmptyFieldHolder.Builder(),
        BDestroyedField::class.java to BDestroyedFieldHolder.Builder()
    )
}