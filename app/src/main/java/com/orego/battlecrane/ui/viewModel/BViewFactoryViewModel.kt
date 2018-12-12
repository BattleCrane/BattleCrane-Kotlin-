package com.orego.battlecrane.ui.viewModel

import androidx.lifecycle.ViewModel
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRender
import com.orego.battlecrane.ui.model.api.scenarioMaterials.BUiScenarioMaterials

class BViewFactoryViewModel : ViewModel() {

    val unitFactory = BUnitViewRender.ViewFactory()

    val buildActionFactory = BActionViewRender.ViewFactory()

    val trainActionFactory = BActionViewRender.ViewFactory()

    val bonusActionFactory = BActionViewRender.ViewFactory()

    fun install(scenarioSupport: BUiScenarioMaterials) {
        scenarioSupport.unitBuilders.forEach { this.unitFactory.addBuilder(it) }
        scenarioSupport.buildingActionBuilders.forEach { this.buildActionFactory.addBuilder(it) }
        scenarioSupport.trainActionBuilders.forEach { this.trainActionFactory.addBuilder(it) }
        scenarioSupport.bonusActionBuilders.forEach { this.bonusActionFactory.addBuilder(it) }
        //Set default builder:
        this.buildActionFactory.defaultBuilder = scenarioSupport.defaultActionBuilder
        this.trainActionFactory.defaultBuilder = scenarioSupport.defaultActionBuilder
        //TODO: MAKE BONUSES:
//        this.bonusActionFactory.defaultBuilder = scenarioSupport.defaultBonusToolBuilder
    }
}