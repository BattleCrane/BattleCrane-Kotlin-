package com.orego.battlecrane.ui.model.api.scenarioSupport

import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.api.scenario.BGameScenario
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.model.api.view.map.BUnitView
import com.orego.battlecrane.ui.model.api.view.tool.BToolView

abstract class BUiScenarioSupport(val gameScenario: BGameScenario) {

    abstract val unitBuilders: Set<BRender.ViewBuilder<BUnit, BUnitView>>

    abstract val buildingToolsBuilders: Set<BRender.ViewBuilder<Class<out BUnit>, BToolView>>

    abstract val trainToolsBuilders: Set<BRender.ViewBuilder<Class<out BUnit>, BToolView>>

    abstract val bonusToolsBuilders: Set<BRender.ViewBuilder<Class<out BAction>, BToolView>>

    abstract val defaultUnitToolBuilder: BRender.ViewBuilder<Class<out BUnit>, BToolView>

//    abstract val defaultBonusToolBuilder: BRender.ViewBuilder<Class<out BAction>, BToolView>
}