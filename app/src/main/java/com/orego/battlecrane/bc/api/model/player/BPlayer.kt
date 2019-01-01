package com.orego.battlecrane.bc.api.model.player

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.node.BOnTurnFinishedNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.node.BOnTurnStartedNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.player.BPlayerComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.timer

class BPlayer(context: BGameContext, builder: BAdjutant.Builder) {

    companion object {

        const val NEUTRAL_PLAYER_ID : Long = 0
    }

    val playerId: Long = context.contextGenerator.getIdGenerator(BPlayer::class.java).generateId()

    //TODO WHILE WITHOUT BONUSES:
    val adjutant: BAdjutant = builder.build(this.playerId)

    var isAblePlayer = true

    private val allies = mutableSetOf<Long>()

    private val enemies = mutableSetOf<Long>()

    /**
     * Context.
     */

    val onTurnStartedPipeId: Long

    val onTurnStartedNodeId : Long
    
    val onTurnFinishedPipeId: Long
    
    val onTurnFinishedNodeId :Long
    
    val turnTimerPipeId : Long
    
    val turnTimerNodeId : Long

    init {
        //Get pipeline:
        val pipeline = context.pipeline

        //Turn timer:
        val turnTimerNode = TurnTimerNode(context, this.playerId)
        val turnTimerPipe = turnTimerNode.wrapInPipe()

        //On turn started:
        val onTurnStartedNode =
            OnTurnStartedNode(context, this.playerId)
        onTurnStartedNode.connectInnerPipe(turnTimerPipe)
        val onTurnStartedPipe = onTurnStartedNode.wrapInPipe()

        //On turn finished:
        val onTurnFinishedNode =
            OnTurnFinishedNode(context, this.playerId)
        val onTurnFinishedPipe = onTurnFinishedNode.wrapInPipe()

        //Save pipe ids:
        this.onTurnStartedPipeId = onTurnStartedPipe.id
        this.onTurnStartedNodeId = onTurnStartedNode.id 
        this.onTurnFinishedPipeId = onTurnFinishedPipe.id
        this.onTurnFinishedNodeId = onTurnFinishedNode.id
        this.turnTimerPipeId = turnTimerPipe.id
        this.turnTimerNodeId = turnTimerNode.id

        //Bind pipes:
        pipeline.bindPipeToNode(BOnTurnStartedNode.NAME, onTurnStartedPipe)
        pipeline.bindPipeToNode(BOnTurnFinishedNode.NAME, onTurnFinishedPipe)
    }

    /**
     * Player.
     */

    fun addEnemy(player: Long) = this.enemies.add(player)

    fun removeEnemy(player: Long) = this.enemies.remove(player)

    fun isEnemy(player: Long) = this.enemies.contains(player)

    fun addAlly(player: Long) = this.allies.add(player)

    fun removeAlly(player: Long) = this.allies.remove(player)

    fun isAlly(player: Long) = this.allies.contains(player)

    @BPlayerComponent
    class OnTurnStartedNode(context: BGameContext, var playerId: Long) : BNode(context) {

        override fun handle(event: BEvent): BEvent? {
            return if (event is BOnTurnStartedPipe.Event
                && event.playerId == this.playerId
            ) {
                event.also { this.pushEventIntoPipes(it) }
            } else {
                null
            }
        }
    }

    @BPlayerComponent
    class OnTurnFinishedNode(context: BGameContext, var playerId: Long) : BNode(context) {

        override fun handle(event: BEvent): BEvent? {
            return if (event is BOnTurnFinishedPipe.Event
                && event.playerId == this.playerId
            ) {
                event.also { this.pushEventIntoPipes(it) }
            } else {
                null
            }
        }
    }

    @BPlayerComponent
    class TurnTimerNode(context: BGameContext, var ownerId: Long) : BNode(context) {

        companion object {

            private const val DEFAULT_TURN_TIME: Long = 45000

            private const val SECOND: Long = 1000

            private const val TIMER_NAME = "TURN_TIMER"
        }

        var turnTime: Long? = DEFAULT_TURN_TIME

        val timeLeft = AtomicLong(0)

        private lateinit var turnTimerTask: TimerTask

        override fun handle(event: BEvent): BEvent? {
            if (event is BTurnPipe.Event && this.ownerId == event.playerId) {
                when (event) {
                    is BOnTurnStartedPipe.Event -> {
                        val turnTime = this.turnTime
                        if (turnTime != null) {
                            this.timeLeft.set(turnTime)
                            timer(name = TIMER_NAME, period = SECOND, action = this.action)
                        }
                    }
                    is BOnTurnFinishedPipe.Event -> {
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
            val time = this@TurnTimerNode.timeLeft.get()
            val isTurnFinished = time <= 0
            if (isTurnFinished) {
                this.cancel()
                this@TurnTimerNode.context.pipeline.pushEvent(
                    BOnTurnFinishedPipe.Event(this@TurnTimerNode.ownerId)
                )
            } else {
                this@TurnTimerNode.timeLeft.set(time - SECOND)
            }
            this@TurnTimerNode.turnTimerTask = this
        }
    }

    /**
     * Generator.
     */


}