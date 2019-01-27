package com.orego.battlecrane.bc.android.standardImpl.location.grass.field

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.engine.standardImpl.location.grass.field.BGrassField

abstract class BUiGrassField protected constructor(uiGameContext: BUiGameContext, item: BGrassField) :
    BUiUnit(uiGameContext, item)