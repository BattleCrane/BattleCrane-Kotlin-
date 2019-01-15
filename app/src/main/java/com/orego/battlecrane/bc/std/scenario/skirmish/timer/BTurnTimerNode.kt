package com.orego.battlecrane.bc.std.scenario.skirmish.timer

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.pipeline.model.component.player.BPlayerComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.timer

@BPlayerComponent
class BTurnTimerNode(context: BGameContext, var playerId: Long) : BNode(context) {

    companion object {

        private const val DEFAULT_TURN_TIME: Long = 45000

        private const val TIMER_NAME = "TURN_TIMER"

        const val SECOND: Long = 1000

        const val NAME = "TURN_TIMER_NODE"
    }

    override val name = NAME

    var turnTime: Long? =
        DEFAULT_TURN_TIME

    val timeLeft = AtomicLong(0)

    private lateinit var turnTimerTask: TimerTask

    /**
     * Context.
     */

    private val pipeline = context.pipeline

    override fun handle(event: BEvent): BEvent? {
        if (event is BTurnPipe.Event && this.playerId == event.playerId) {
            when (event) {
                is BOnTurnStartedPipe.Event -> {
                    val turnTime = this.turnTime
                    if (turnTime != null) {
                        this.timeLeft.set(turnTime)
                        this.pushToInnerPipes(
                            StartEvent(
                                this.playerId,
                                turnTime
                            )
                        )
                        timer(name = TIMER_NAME, period = SECOND, action = this.action)
                    }
                }
                is BOnTurnFinishedPipe.Event -> {
                    this.pushToInnerPipes(
                        StopEvent(
                            this.playerId
                        )
                    )
                    this.turnTimerTask.cancel()
                }
            }
            return event
        } else {
            return null
        }
    }

    /**
     * Clumsy timer.
     */

    private val action: TimerTask.() -> Unit = {
        val timeLeft = this@BTurnTimerNode.timeLeft.get()
        val isTurnFinished = timeLeft <= 0
        if (isTurnFinished) {
            this.cancel()
            this@BTurnTimerNode.pipeline.pushEvent(BOnTurnFinishedPipe.Event(this@BTurnTimerNode.playerId))
        } else {
            this@BTurnTimerNode.timeLeft.set(timeLeft - SECOND)
            this@BTurnTimerNode.pushToInnerPipes(
                TickEvent(
                    this@BTurnTimerNode.playerId,
                    this@BTurnTimerNode.timeLeft.get()
                )
            )
        }
        this@BTurnTimerNode.turnTimerTask = this
    }

    class TickEvent(playerId: Long, val timeLeft: Long) : BTurnPipe.Event(playerId)

    class StartEvent(playerId: Long, val turnTime: Long) : BTurnPipe.Event(playerId)

    class StopEvent(playerId: Long) : BTurnPipe.Event(playerId)
}