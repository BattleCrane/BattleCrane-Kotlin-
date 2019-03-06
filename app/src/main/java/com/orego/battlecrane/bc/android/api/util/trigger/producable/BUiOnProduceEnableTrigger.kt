package com.orego.battlecrane.bc.android.api.util.trigger.producable

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.util.trigger.enable.BUiOnEnableTrigger
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.node.pipe.onProduceEnable.BOnProduceEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.model.util.BProducable
import com.orego.battlecrane.bc.engine.api.util.trigger.producable.BOnProduceEnableTrigger

class BUiOnProduceEnableTrigger private constructor(uiGameContext: BUiGameContext, uiUnit: BUiUnit) :
    BUiOnEnableTrigger(uiGameContext, uiUnit) {

    override fun mathchesEvent(event: BEvent?) = event is BOnProduceEnablePipe.Event
            && event.producableId == (this.uiUnit.unit as BProducable).producableId

    override fun BEvent.isEnable() = (this as BOnProduceEnablePipe.Event).isEnable

    override fun onEnable() {
        this.uiUnit.activate(this.uiGameContext)
    }

    override fun onDisable() {
        this.uiUnit.dismiss(this.uiGameContext)
    }

    companion object {

        fun connect(uiGameContext: BUiGameContext, unit: BUiUnit) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnProduceEnableTrigger && node.producable == unit.unit
            }
            val uiTrigger = BUiOnProduceEnableTrigger(uiGameContext, unit)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}