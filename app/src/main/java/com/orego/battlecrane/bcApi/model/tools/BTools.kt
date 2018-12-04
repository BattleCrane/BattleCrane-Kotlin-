package com.orego.battlecrane.bcApi.model.tools

import com.orego.battlecrane.bcApi.model.bonus.BBonus
import com.orego.battlecrane.bcApi.model.unit.BUnit

open class BTools(
    val buildingStack: MutableList<Class<out BUnit>>,
    val armyStack: MutableList<Class<out BUnit>>,
    val bonusStack: MutableList<Class<out BBonus>>
)
