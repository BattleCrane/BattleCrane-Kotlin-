package com.orego.battlecrane.ui.viewModel

import androidx.lifecycle.ViewModel
import com.orego.battlecrane.ui.model.api.scenarioMaterials.BUiScenarioMaterials
import com.orego.battlecrane.ui.model.std.scenarioMaterials.BUiStandardSkirmishScenarioMaterials

class BUiScenarioSupportViewModel : ViewModel() {

    //TODO: WHILE HUMAN, MAKE FLEXIBLE UI!!!
    companion object {

        const val BUILD = "race/human/button/build.png"

        const val TRAIN = "race/human/button/train.png"
    }

    //TODO: MAKE GAME SCENARIO: THIS IS FOR TEST!!!
    var scenarioMaterials: BUiScenarioMaterials = BUiStandardSkirmishScenarioMaterials()
}