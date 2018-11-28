package com.orego.battlecrane.ui.viewModel

import androidx.lifecycle.ViewModel
import com.orego.battlecrane.bcApi.scenario.BGameScenario
import com.orego.battlecrane.bcApi.scenario.skirmish.BSkirmishScenario

class BGameScenarioViewModel : ViewModel() {

    //TODO: MAKE GAME SCENARIO: THIS IS FOR TEST
    var scenario: BGameScenario = BSkirmishScenario()
}