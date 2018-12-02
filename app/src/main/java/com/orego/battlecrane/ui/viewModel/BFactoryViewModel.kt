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

    fun install(scenarioProvider: BScenarioProvider) {
        scenarioProvider.unitBuilders.forEach { this.mapFactory.addViewHolderBuilder(it::class.java, it) }
        scenarioProvider.bonusToolsBuilders
    }
}