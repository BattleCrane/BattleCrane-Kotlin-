package com.orego.battlecrane.bc.android.api.util.trigger.producable

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.util.trigger.enable.BUiOnEnableTrigger
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.model.util.BProducable

abstract class BUiOnProduceEnableTrigger(uiGameContext: BUiGameContext, uiUnit: BUiUnit) :
    BUiOnEnableTrigger(uiGameContext, uiUnit) {

    override fun mathchesEvent(event: BEvent?) = event is BOnProduceEnablePipe.Event
            && event.producableId == (this.uiUnit.item as BProducable).producableId

    override fun BEvent.isEnable() = (this as BOnProduceEnablePipe.Event).isEnable
}