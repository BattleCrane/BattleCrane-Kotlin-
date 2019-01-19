package com.orego.battlecrane.ui.viewModel

import androidx.lifecycle.ViewModel
import com.orego.battlecrane.bc.android.api.scenario.BUiGameScenario
import com.orego.battlecrane.bc.android.scenario.skirmish.BUiSkirmishScenario

class BScenarioViewModel : ViewModel() {

    /**
     * Scenario.
     */

    var uiGameScenario: BUiGameScenario? = null

    //TODO: SEVERAL TIME!
    init {
        this.uiGameScenario = BUiSkirmishScenario()
    }
}