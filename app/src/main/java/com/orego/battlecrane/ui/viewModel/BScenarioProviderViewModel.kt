package com.orego.battlecrane.ui.viewModel

import androidx.lifecycle.ViewModel
import com.orego.battlecrane.ui.model.scenarioProvider.BScenarioProvider
import com.orego.battlecrane.uistd.scenarioProvider.BStandardSkirmishScenarioProvider

class BScenarioProviderViewModel : ViewModel() {

    //TODO: MAKE GAME SCENARIO: THIS IS FOR TEST
    var scenarioProvider: BScenarioProvider = BStandardSkirmishScenarioProvider()
}