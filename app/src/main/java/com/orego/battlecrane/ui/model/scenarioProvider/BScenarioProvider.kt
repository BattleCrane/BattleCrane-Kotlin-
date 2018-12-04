package com.orego.battlecrane.ui.model.scenarioProvider

import com.orego.battlecrane.bcApi.scenario.BGameScenario
import com.orego.battlecrane.bcApi.model.unit.BUnit
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.model.viewHolder.map.BUnitViewHolder
import com.orego.battlecrane.ui.model.viewHolder.tool.BToolViewHolder

abstract class BScenarioProvider(val gameScenario: BGameScenario) {

    abstract val unitBuilders : Set<BRender.ViewHolderBuilder<BUnit, BUnitViewHolder>>

    abstract val buildingToolsBuilders : Set<BRender.ViewHolderBuilder<Class<out BUnit>, BToolViewHolder>>

    abstract val trainToolsBuilders : Set<BRender.ViewHolderBuilder<Class<out BUnit>, BToolViewHolder>>

    abstract val bonusToolsBuilders : Set<BRender.ViewHolderBuilder<Class<out BUnit>, BToolViewHolder>>
}