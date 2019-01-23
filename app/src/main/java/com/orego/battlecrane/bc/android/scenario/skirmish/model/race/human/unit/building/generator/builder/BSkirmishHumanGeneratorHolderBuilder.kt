package com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.generator.builder

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.holder.unit.trigger.BOnDestroyUnitHolderTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.generator.trigger.BSkirmishHumanGeneratorHolderOnHitPointsActionTrigger
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.unit.building.generator.trigger.BSkirmishHumanGeneratorHolderOnProduceEnableTrigger
import com.orego.battlecrane.bc.android.standardImpl.race.human.unit.building.BHumanGeneratorHolder
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

class BSkirmishHumanGeneratorHolderBuilder : BHumanGeneratorHolder.Builder() {

    override fun build(uiGameContext: BUiGameContext, item: BUnit): BHumanGeneratorHolder {
        val holder = super.build(uiGameContext, item)
        BOnDestroyUnitHolderTrigger.connect(uiGameContext, holder)
        BSkirmishHumanGeneratorHolderOnProduceEnableTrigger.connect(uiGameContext, holder)
        BSkirmishHumanGeneratorHolderOnHitPointsActionTrigger.connect(uiGameContext, holder)
        return holder
    }
}