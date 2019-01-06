package com.orego.battlecrane.ui.model.api.scenarioMaterials

import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRender

abstract class BUiScenarioMaterials {

    abstract val unitBuilders: Set<BUnitViewRender.ViewBuilder>

    abstract val buildingActionBuilders: Set<BActionViewRender.ViewBuilder>

    abstract val trainActionBuilders: Set<BActionViewRender.ViewBuilder>

    abstract val bonusActionBuilders: Set<BActionViewRender.ViewBuilder>
}