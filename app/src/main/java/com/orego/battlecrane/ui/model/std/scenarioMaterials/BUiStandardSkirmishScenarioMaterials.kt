package com.orego.battlecrane.ui.model.std.scenarioMaterials

import com.orego.battlecrane.bc.std.scenario.skirmish.BStandardSkirmishScenario
import com.orego.battlecrane.ui.model.api.render.action.BActionViewRenderItem
import com.orego.battlecrane.ui.model.api.render.unit.BUnitViewRenderItem
import com.orego.battlecrane.ui.model.api.scenarioMaterials.BUiScenarioMaterials
import com.orego.battlecrane.ui.model.std.view.ground.map.field.destroyed.BDestroyedFieldView
import com.orego.battlecrane.ui.model.std.view.ground.map.field.empty.BEmptyFieldView
import com.orego.battlecrane.ui.model.std.view.race.human.BHumanViewSuppliers

class BUiStandardSkirmishScenarioMaterials : BUiScenarioMaterials(BStandardSkirmishScenario()) {

    override val unitBuilders = mutableSetOf<BUnitViewRenderItem.ViewBuilder>()

    private val humanViewBuilders = BHumanViewSuppliers()

    //TODO: MAKE BONUS:
//    override val defaultBonusToolBuilder: BUiItemShell.BUiItemSupplier<Class<out BAction>, BActionView>

    init {
        //Field:
        this.unitBuilders.addAll(
            setOf(BEmptyFieldView.Builder(), BDestroyedFieldView.Builder())
        )
        //Human:
        this.unitBuilders.addAll(this.humanViewBuilders.units)
    }

    override val buildingActionBuilders = mutableSetOf<BActionViewRenderItem.ViewBuilder>()

    init {
        this.buildingActionBuilders.addAll(this.humanViewBuilders.buildingTools)
    }

    override val trainActionBuilders = mutableSetOf<BActionViewRenderItem.ViewBuilder>(

    )

    init {
        this.trainActionBuilders.addAll(this.humanViewBuilders.armyTools)
    }

    override val bonusActionBuilders = mutableSetOf<BActionViewRenderItem.ViewBuilder>(

    )

    //TODO: MAKE BONUSES!!!
//    init {
//        this.bonusActionBuilders.addAll(BHumanViewSuppliers.bonusTools)
//    }
}