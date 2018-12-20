package com.orego.battlecrane.ui.viewModel

import androidx.lifecycle.ViewModel
import com.orego.battlecrane.ui.model.api.scenarioMaterials.BUiScenarioMaterials
import com.orego.battlecrane.ui.model.std.scenarioMaterials.BUiStandardSkirmishScenarioMaterials

class BUiScenarioSupportViewModel : ViewModel() {

    //TODO: MAKE GAME SCENARIO: THIS IS FOR TEST!!!
    var scenarioMaterials: BUiScenarioMaterials = BUiStandardSkirmishScenarioMaterials()
}