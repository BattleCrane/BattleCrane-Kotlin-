package com.orego.battlecrane.bc.engine.api.util.trigger.turn

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.timer

class BTurnTimerTrigger private constructor(context: BGameContext, var playerId: Long) : BNode(context) {

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
        val timeLeft = this@BTurnTimerTrigger.timeLeft.get()
        val isTurnFinished = timeLeft <= 0
        if (isTurnFinished) {
            this.cancel()
            this@BTurnTimerTrigger.pipeline.pushEvent(BOnTurnFinishedPipe.Event(this@BTurnTimerTrigger.playerId))
        } else {
            this@BTurnTimerTrigger.timeLeft.set(timeLeft - SECOND)
            this@BTurnTimerTrigger.pushToInnerPipes(
                TickEvent(
                    this@BTurnTimerTrigger.playerId,
                    this@BTurnTimerTrigger.timeLeft.get()
                )
            )
        }
        this@BTurnTimerTrigger.turnTimerTask = this
    }

    /**
     * Event.
     */

    class TickEvent(playerId: Long, val timeLeft: Long) : BTurnPipe.Event(playerId)

    class StartEvent(playerId: Long, val turnTime: Long) : BTurnPipe.Event(playerId)

    class StopEvent(playerId: Long) : BTurnPipe.Event(playerId)

    companion object {

        private const val DEFAULT_TURN_TIME: Long = 45000

        private const val TIMER_NAME = "TURN_TIMER"

        const val SECOND: Long = 1000

        const val NAME = "TURN_TIMER_NODE"
    }
}