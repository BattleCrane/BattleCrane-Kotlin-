package com.orego.battlecrane.ui.model.api.scenarioSupport

import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.scenario.BGameScenario
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.ui.model.api.render.BViewRender
import com.orego.battlecrane.ui.model.api.view.map.BUnitView
import com.orego.battlecrane.ui.model.api.view.tool.BToolView

abstract class BUiScenarioSupport(val gameScenario: BGameScenario) {

    abstract val unitBuilders: Set<BViewRender.ViewBuilder<BUnit, BUnitView>>

    abstract val buildingToolsBuilders: Set<BViewRender.ViewBuilder<Class<out BUnit>, BToolView>>

    abstract val trainToolsBuilders: Set<BViewRender.ViewBuilder<Class<out BUnit>, BToolView>>

    abstract val bonusToolsBuilders: Set<BViewRender.ViewBuilder<Class<out BAction>, BToolView>>

    abstract val defaultUnitToolBuilder: BViewRender.ViewBuilder<Class<out BUnit>, BToolView>

//    abstract val defaultBonusToolBuilder: BViewRender.ViewBuilder<Class<out BAction>, BToolView>
}