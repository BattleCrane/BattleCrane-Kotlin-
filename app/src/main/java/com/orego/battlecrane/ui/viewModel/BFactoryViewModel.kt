package com.orego.battlecrane.ui.viewModel

import androidx.lifecycle.ViewModel
import bcApi.bonus.BBonus
import bcApi.unit.BUnit
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.model.scenarioProvider.BScenarioProvider
import com.orego.battlecrane.ui.model.viewHolder.map.BUnitViewHolder
import com.orego.battlecrane.ui.model.viewHolder.tool.BToolViewHolder

class BFactoryViewModel : ViewModel() {

    val mapFactory = BRender.ViewHolderFactory<BUnit, BUnitViewHolder>()

    val buildToolFactory = BRender.ViewHolderFactory<Class<out BUnit>, BToolViewHolder>()

    val trainToolFactory = BRender.ViewHolderFactory<Class<out BUnit>, BToolViewHolder>()

    val bonusToolFactory = BRender.ViewHolderFactory<Class<out BBonus>, BToolViewHolder>()

    fun install(provider: BScenarioProvider) {
        provider.unitBuilders.forEach { this.mapFactory.addBuilder(it) }
        provider.buildingToolsBuilders.forEach { this.buildToolFactory.addBuilder(it) }
        provider.trainToolsBuilders.forEach { this.trainToolFactory.addBuilder(it) }
        provider.bonusToolsBuilders.forEach { this.buildToolFactory.addBuilder(it) }
    }
}