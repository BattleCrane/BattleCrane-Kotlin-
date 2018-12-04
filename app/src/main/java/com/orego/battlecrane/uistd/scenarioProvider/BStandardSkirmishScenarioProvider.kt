package com.orego.battlecrane.uistd.scenarioProvider

import com.orego.battlecrane.bcApi.scenario.skirmish.BStandardSkirmishScenario
import com.orego.battlecrane.bcApi.model.unit.BUnit
import com.orego.battlecrane.ui.fragment.battle.render.BRender
import com.orego.battlecrane.ui.model.scenarioProvider.BScenarioProvider
import com.orego.battlecrane.ui.model.viewHolder.map.BUnitViewHolder
import com.orego.battlecrane.ui.model.viewHolder.tool.BToolViewHolder
import com.orego.battlecrane.uistd.viewHolder.map.field.destroyed.BDestroyedFieldViewHolder
import com.orego.battlecrane.uistd.viewHolder.map.field.empty.BEmptyFieldViewHolder
import com.orego.battlecrane.uistd.viewHolder.map.race.human.BHumanViewHolderBuilders

class BStandardSkirmishScenarioProvider : BScenarioProvider(BStandardSkirmishScenario()) {

    override val unitBuilders = mutableSetOf<BRender.ViewHolderBuilder<BUnit, BUnitViewHolder>>()

    init {
        //Field:
        this.unitBuilders.addAll(
            setOf(
                BEmptyFieldViewHolder.Builder(),
                BDestroyedFieldViewHolder.Builder()
            )
        )
        //Human:
        this.unitBuilders.addAll(BHumanViewHolderBuilders.units)
    }

    override val buildingToolsBuilders = mutableSetOf<BRender.ViewHolderBuilder<Class<out BUnit>, BToolViewHolder>>()

    init {
        this.buildingToolsBuilders.addAll(BHumanViewHolderBuilders.buildingTools)
    }

    override val trainToolsBuilders: Set<BRender.ViewHolderBuilder<Class<out BUnit>, BToolViewHolder>> = setOf(

    )

    override val bonusToolsBuilders: Set<BRender.ViewHolderBuilder<Class<out BUnit>, BToolViewHolder>> = setOf(

    )
}