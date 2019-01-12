package com.orego.battlecrane.bc.api.model.player

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.node.BOnTurnFinishedNode
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.BOnTurnStartedPipe
import com.orego.battlecrane.bc.api.context.pipeline.implementation.turn.node.pipe.onTurnStarted.node.BOnTurnStartedNode
import com.orego.battlecrane.bc.api.context.pipeline.model.component.player.BPlayerComponent
import com.orego.battlecrane.bc.api.context.pipeline.model.event.BEvent
import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipeConnection
import com.orego.battlecrane.bc.api.context.storage.heap.implementation.BAdjutantHeap

class BPlayer(context: BGameContext) {

    companion object {

        const val NEUTRAL_PLAYER_ID : Long = 0
    }

    /**
     * Id.
     */

    val playerId: Long = context.contextGenerator.getIdGenerator(BPlayer::class.java).generateId()

    /**
     * Property.
     */

    fun isAblePlayer(context: BGameContext) : Boolean {
        val adjutantHeap = context.storage.getHeap(BAdjutantHeap::class.java)
        for (id in this.adjutants) {
            val adjutant = adjutantHeap[id]
            if (adjutant.isAble) {
                return true
            }
        }
        return false
    }

    val adjutants = mutableSetOf<Long>()

    val allies = mutableSetOf<Long>()

    val enemies = mutableSetOf<Long>()

    /**
     * Context.
     */

    val turnStartedConnection = BPipeConnection.createByNode(
        context, BOnTurnStartedNode.NAME, OnTurnStartedNode(context, this.playerId)
    )

    val turnFinishedConnection = BPipeConnection.createByNode(
        context, BOnTurnFinishedNode.NAME, OnTurnFinishedNode(context, this.playerId)
    )

    /**
     * Player.
     */

    fun addEnemy(player: Long) = this.enemies.add(player)

    fun removeEnemy(player: Long) = this.enemies.remove(player)

    fun isMine(player: Long) = this.playerId == player

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
}