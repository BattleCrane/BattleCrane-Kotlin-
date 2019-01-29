package com.orego.battlecrane.bc.android.api.util.trigger.attackable

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.util.trigger.enable.BUiOnEnableTrigger
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.BOnAttackEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.model.property.BAttackable

abstract class BUiOnAttackEnableTrigger(uiGameContext: BUiGameContext, uiUnit: BUiUnit) :
    BUiOnEnableTrigger(uiGameContext, uiUnit) {

    override fun mathchesEvent(event: BEvent?) = event is BOnAttackEnablePipe.Event
            && event.attackableId == (this.uiUnit.item as BAttackable).attackableId

    override fun BEvent.isEnable() = (this as BOnAttackEnablePipe.Event).isEnable
}