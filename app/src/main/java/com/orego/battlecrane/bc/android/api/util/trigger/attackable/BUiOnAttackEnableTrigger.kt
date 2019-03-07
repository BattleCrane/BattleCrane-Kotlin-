package com.orego.battlecrane.bc.android.api.util.trigger.attackable

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.model.unit.BUiUnit
import com.orego.battlecrane.bc.android.api.util.trigger.enable.BUiOnEnableTrigger
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.node.pipe.onAttackEnable.BOnAttackEnablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.model.util.BAttackable
import com.orego.battlecrane.bc.engine.api.util.trigger.attack.BOnAttackEnableTrigger

class BUiOnAttackEnableTrigger(uiGameContext: BUiGameContext, uiUnit: BUiUnit) :
    BUiOnEnableTrigger(uiGameContext, uiUnit) {

    override fun mathchesEvent(event: BEvent?) = event is BOnAttackEnablePipe.Event
            && event.attackableId == (this.uiUnit.unit as BAttackable).attackableId

    override fun BEvent.isEnable() = (this as BOnAttackEnablePipe.Event).isEnable


    companion object {

        private const val COLUMN_COUNT = 2

        fun connect(uiGameContext: BUiGameContext, holder: BUiUnit) {
            val trigger = uiGameContext.gameContext.pipeline.findNodeBy { node ->
                node is BOnAttackEnableTrigger && node.attackable == holder.unit
            }
            val uiTrigger = BUiOnAttackEnableTrigger(uiGameContext, holder)
            trigger.connectInnerPipe(uiTrigger.intoPipe())
        }
    }
}