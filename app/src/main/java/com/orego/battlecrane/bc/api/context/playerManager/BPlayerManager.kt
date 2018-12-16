package com.orego.battlecrane.bc.api.context.playerManager

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.playerManager.player.BPlayer
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.api.scenario.BGameScenario
import com.orego.battlecrane.bc.api.util.BIdGenerator
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.timer

class BPlayerManager(scenario: BGameScenario, context: BGameContext) {

    companion object {

        private const val DEFAULT_TURN_TIME: Long = 45000

        private const val SECOND: Long = 1000

        private const val TIMER_NAME = "TURN_TIMER"
    }

    val players: List<BPlayer> = scenario.initPlayerList(context)

    var currentPlayer: BPlayer = scenario.getStartPlayer(this.players)

    var playerPointer  = this.players.indexOf(this.currentPlayer)

    val turnAssistant = BTurnAssistant()

    fun isEnemies(unit1: BUnit, unit2: BUnit): Boolean {
        val owner1 = unit1.owner
        val owner2 = unit2.owner
        return owner1 != null && owner2 != null && owner1.isEnemy(owner2)
    }

    fun onGameStarted() {
        this.turnAssistant.startTurn(this.currentPlayer)
    }

    fun onGameFinished() {

    }


    inner class BTurnAssistant {

        private val listenerId = BIdGenerator.generateActionId()

        val timeLeft = AtomicLong(0)

        val onPlayerTurnFinished = object : BPlayer.OnPlayerTurnFinishedListener {

            override fun onPlayerTurnFinished(player: BPlayer) {
                player.onTurnFinishedObserver.remove(this@BTurnAssistant.listenerId)
                this@BTurnAssistant.finishTurn()
            }
        }

        fun startTurn(player: BPlayer, turnTime: Long? = DEFAULT_TURN_TIME) {
            player.onTurnFinishedObserver[listenerId] = this.onPlayerTurnFinished
            if (turnTime != null) {
                player.startTurn()
                this.timeLeft.set(turnTime)
                timer(name = TIMER_NAME, period = SECOND) {
                    val time = timeLeft.get()
                    if (time > 0) {
                        this@BTurnAssistant.timeLeft.set(time - SECOND)
                    } else {
                        player.finishTurn()
                        this.cancel()
                    }
                }
            }
        }

        fun finishTurn() {
            val players = this@BPlayerManager.players
            if (this@BPlayerManager.playerPointer < players.size) {
                this@BPlayerManager.playerPointer++
            } else {
                this@BPlayerManager.playerPointer = 0
            }
            val nextPlayer = players[this@BPlayerManager.playerPointer]
            this@BPlayerManager.currentPlayer = nextPlayer
            this.startTurn(nextPlayer)
        }
    }
}