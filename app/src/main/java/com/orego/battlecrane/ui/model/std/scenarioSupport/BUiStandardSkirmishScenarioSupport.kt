package com.orego.battlecrane.ui.model.std.scenarioSupport

import com.orego.battlecrane.bc.api.model.action.BAction
import com.orego.battlecrane.bc.std.scenario.skirmish.BStandardSkirmishScenario
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.model.api.scenarioSupport.BUiScenarioSupport
import com.orego.battlecrane.ui.model.api.view.map.BUnitView
import com.orego.battlecrane.ui.model.api.view.tool.BToolView
import com.orego.battlecrane.ui.model.std.view.ground.map.field.destroyed.BDestroyedFieldView
import com.orego.battlecrane.ui.model.std.view.ground.map.field.empty.BEmptyFieldView
import com.orego.battlecrane.ui.model.std.view.ground.tool.empty.BEmptyToolView
import com.orego.battlecrane.ui.model.std.view.race.human.BHumanViewBuilders

class BUiStandardSkirmishScenarioSupport : BUiScenarioSupport(BStandardSkirmishScenario()) {

    override val unitBuilders = mutableSetOf<BRender.ViewBuilder<BUnit, BUnitView>>()

    override val defaultUnitToolBuilder = BEmptyToolView.Builder()

    //TODO: MAKE BONUS:
//    override val defaultBonusToolBuilder: BRender.ViewBuilder<Class<out BAction>, BToolView>

    init {
        //Field:
        this.unitBuilders.addAll(
            setOf(
                BEmptyFieldView.Builder(),
                BDestroyedFieldView.Builder()
            )
        )
        //Human:
        this.unitBuilders.addAll(BHumanViewBuilders.units)
    }

    override val buildingToolsBuilders = mutableSetOf<BRender.ViewBuilder<Class<out BUnit>, BToolView>>()

    init {
        this.buildingToolsBuilders.addAll(BHumanViewBuilders.buildingTools)
    }

    override val trainToolsBuilders = mutableSetOf<BRender.ViewBuilder<Class<out BUnit>, BToolView>>(

    )

    init {
        this.trainToolsBuilders.addAll(BHumanViewBuilders.armyTools)
    }

    override val bonusToolsBuilders = mutableSetOf<BRender.ViewBuilder<Class<out BAction>, BToolView>>(

    )

    //TODO: MAKE BONUSES!!!
//    init {
//        this.bonusToolsBuilders.addAll(BHumanViewBuilders.bonusTools)
//    }
}