package com.orego.battlecrane.ui.viewModel

import androidx.lifecycle.ViewModel
import com.orego.battlecrane.ui.model.scenarioProvider.BScenarioProvider
import com.orego.battlecrane.uistdlib.model.scenarioProvider.BStandardSkirmishScenarioProvider

class BScenarioProviderViewModel : ViewModel() {

    //TODO: MAKE GAME SCENARIO: THIS IS FOR TEST
    var supporter: BScenarioProvider = BStandardSkirmishScenarioProvider()
}