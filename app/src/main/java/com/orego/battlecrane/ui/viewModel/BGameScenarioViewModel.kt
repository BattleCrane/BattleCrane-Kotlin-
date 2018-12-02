package com.orego.battlecrane.ui.viewModel

import androidx.lifecycle.ViewModel
import bcApi.scenario.BGameScenario
import bcApi.scenario.skirmish.BSkirmishScenario

class BGameScenarioViewModel : ViewModel() {

    //TODO: MAKE GAME SCENARIO: THIS IS FOR TEST
    var scenario: BGameScenario = BSkirmishScenario()
}