package com.orego.battlecrane.ui.viewModel

import androidx.lifecycle.ViewModel
import com.orego.battlecrane.ui.model.api.scenarioSupport.BUiScenarioSupport
import com.orego.battlecrane.ui.model.std.scenarioSupport.BUiStandardSkirmishScenarioSupport

class BUiScenarioSupportViewModel : ViewModel() {

    //TODO: MAKE GAME SCENARIO: THIS IS FOR TEST!!!
    var uiScenarioSupport: BUiScenarioSupport = BUiStandardSkirmishScenarioSupport()
}