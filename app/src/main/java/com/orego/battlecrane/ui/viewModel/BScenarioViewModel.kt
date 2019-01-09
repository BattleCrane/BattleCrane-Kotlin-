package com.orego.battlecrane.ui.viewModel

import androidx.lifecycle.ViewModel
import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.entity.main.unit.BUnit
import com.orego.battlecrane.bc.api.scenario.BGameScenario
import com.orego.battlecrane.ui.model.api.holder.BHolder
import com.orego.battlecrane.ui.model.api.holder.adjutant.BAdjutantHolder
import com.orego.battlecrane.ui.model.api.holder.unit.BUnitHolder
import com.orego.battlecrane.ui.model.api.util.RaceFactory

class BScenarioViewModel : ViewModel() {

    var scenario: BGameScenario? = null

    val uiUnitFactory: BHolder.Factory<BUnit> = BUnitHolder.Factory()

    val uiAdjutantFactory: BHolder.Factory<BAdjutant> = BAdjutantHolder.Factory()

    fun addRaceFactory(factory: RaceFactory) {
        val adjutantBuilder = factory.uiAdjutantBuilder
        val unitBuilders = factory.uiUnitBuilders
        this.uiAdjutantFactory.addBuilder(adjutantBuilder.first, adjutantBuilder.second)
        for (entry in unitBuilders) {
            this.uiUnitFactory.addBuilder(entry)
        }
    }
}