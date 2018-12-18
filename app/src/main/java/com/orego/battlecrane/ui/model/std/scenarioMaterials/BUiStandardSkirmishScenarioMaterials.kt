package com.orego.battlecrane.ui.model.std.scenarioMaterials

import com.orego.battlecrane.bc.std.scenario.skirmish.BStandardSkirmishScenario
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRender
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRender
import com.orego.battlecrane.ui.model.api.scenarioMaterials.BUiScenarioMaterials
import com.orego.battlecrane.ui.model.std.view.ground.map.field.destroyed.BDestroyedFieldView
import com.orego.battlecrane.ui.model.std.view.ground.map.field.empty.BEmptyFieldView
import com.orego.battlecrane.ui.model.std.view.race.human.BHumanViewBuilders

class BUiStandardSkirmishScenarioMaterials : BUiScenarioMaterials(BStandardSkirmishScenario()) {

    override val unitBuilders = mutableSetOf<BUnitViewRender.ViewBuilder>()

    private val humanViewBuilders = BHumanViewBuilders()

    //TODO: MAKE BONUS:
//    override val defaultBonusToolBuilder: BViewRender.ViewBuilder<Class<out BAction>, BActionView>

    init {
        //Field:
        this.unitBuilders.addAll(
            setOf(BEmptyFieldView.Builder(), BDestroyedFieldView.Builder())
        )
        //Human:
        this.unitBuilders.addAll(this.humanViewBuilders.units)
    }

    override val buildingActionBuilders = mutableSetOf<BActionViewRender.ViewBuilder>()

    init {
        this.buildingActionBuilders.addAll(this.humanViewBuilders.buildingTools)
    }

    override val trainActionBuilders = mutableSetOf<BActionViewRender.ViewBuilder>(

    )

    init {
        this.trainActionBuilders.addAll(this.humanViewBuilders.armyTools)
    }

    override val bonusActionBuilders = mutableSetOf<BActionViewRender.ViewBuilder>(

    )

    //TODO: MAKE BONUSES!!!
//    init {
//        this.bonusActionBuilders.addAll(BHumanViewBuilders.bonusTools)
//    }
}