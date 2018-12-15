package com.orego.battlecrane.bc.api.context.playerManager.player.turnTimer

import com.orego.battlecrane.bc.api.context.playerManager.player.adjutant.BAdjutant
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.timer

class BTurnTimer(
    private val adjutant : BAdjutant,
    private val turnTime: Long = DEFAULT_TURN_TIME
) {

    companion object {

        private const val DEFAULT_TURN_TIME: Long = 45000

        private const val SECOND: Long = 1000

        private const val TIMER_NAME = "TURN_TIMER"
    }

    val timeLeft = AtomicLong(this.turnTime)

    fun launch() {
        this.timeLeft.set(this.turnTime)
        this.adjutant.onTurnStarted()
        timer(name = TIMER_NAME, period = SECOND) {
            val time = timeLeft.get()
            if (time > 0) {
                this@BTurnTimer.timeLeft.set(time - SECOND)
            } else {
                this@BTurnTimer.adjutant.onTurnEnded()
                this.cancel()
            }
        }
    }
}
