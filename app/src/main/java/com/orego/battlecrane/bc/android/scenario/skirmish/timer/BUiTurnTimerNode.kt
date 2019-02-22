package com.orego.battlecrane.bc.android.scenario.skirmish.timer

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.model.player.BPlayer
import com.orego.battlecrane.bc.engine.api.util.trigger.turn.BTurnTimerTrigger

class BUiTurnTimerNode(private val uiContext: BUiGameContext, private val playerId: Long) :
    BNode(uiContext.gameContext) {

    companion object {

        private const val DEFAULT_MIN = 0

        fun connect(uiContext: BUiGameContext, player: BPlayer) {
            val playerId = player.playerId
            val turnTimerNode = uiContext.gameContext.pipeline.findNodeBy { node ->
                node is BTurnTimerTrigger && node.playerId == playerId
            }
            turnTimerNode.connectInnerPipe(
                BUiTurnTimerNode(
                    uiContext,
                    playerId
                ).intoPipe())
        }
    }

    override fun handle(event: BEvent): BEvent? {
        val animation: suspend () -> Unit = when (event) {
            is BTurnTimerTrigger.StartEvent -> {
                {
                }
            }
            is BTurnTimerTrigger.StopEvent -> {
                {
                }
            }
            is BTurnTimerTrigger.TickEvent -> {
                {
                }
            }
            else -> {
                {}
            }
        }
        this.uiContext.uiTaskManager.addTask(animation)
        return null
    }
}