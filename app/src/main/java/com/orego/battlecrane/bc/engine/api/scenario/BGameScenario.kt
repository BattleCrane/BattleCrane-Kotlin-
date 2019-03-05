package com.orego.battlecrane.bc.engine.api.scenario

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.controller.map.BMapController
import com.orego.battlecrane.bc.engine.api.context.generator.BContextGenerator
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.BAttackablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.BHitPointablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.levelable.BLevelablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.BProducablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.BUnitPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.*
import com.orego.battlecrane.bc.engine.api.model.player.BPlayer
import com.orego.battlecrane.bc.engine.api.model.util.BAttackable
import com.orego.battlecrane.bc.engine.api.model.util.BHitPointable
import com.orego.battlecrane.bc.engine.api.model.util.BLevelable
import com.orego.battlecrane.bc.engine.api.model.util.BProducable
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.api.scenario.util.plugin.location.BLocationPlugin

/**
 * Initializes a game step by step.
 */

abstract class BGameScenario {

    /**
     * Configures context.
     */

    open fun install(context: BGameContext) {
        this.installContextGenerator(context)
        this.installStorage(context)
        this.installPipeline(context)
        this.installBaseTriggers(context)
        this.installPlayers(context)
        this.installPlayerController(context)
        this.installUnits(context)
        this.installMapController(context)
    }

    protected open fun installContextGenerator(context: BGameContext) {
        context.contextGenerator.generatorMap.apply {
            this[BPipe::class.java] = BContextGenerator.IdGenerator(0)
            this[BNode::class.java] = BContextGenerator.IdGenerator(0)
            this[BPlayer::class.java] = BContextGenerator.IdGenerator(1)
            this[BUnit::class.java] = BContextGenerator.IdGenerator(0)
            this[BAttackable::class.java] = BContextGenerator.IdGenerator(0)
            this[BHitPointable::class.java] = BContextGenerator.IdGenerator(0)
            this[BLevelable::class.java] = BContextGenerator.IdGenerator(0)
            this[BProducable::class.java] = BContextGenerator.IdGenerator(0)
        }
    }

    protected open fun installStorage(context: BGameContext) {
        context.storage.apply {
            this.installHeap(BPlayerHeap())
            this.installHeap(BUnitHeap())
            this.installHeap(BAttackableHeap())
            this.installHeap(BLevelableHeap())
            this.installHeap(BHitPointableHeap())
            this.installHeap(BProducableHeap())
        }
    }

    protected open fun installPipeline(context: BGameContext) {
        context.pipeline.apply {
            this.connectInnerPipe(BUnitPipe(context))
            this.connectInnerPipe(BAttackablePipe(context))
            this.connectInnerPipe(BHitPointablePipe(context))
            this.connectInnerPipe(BLevelablePipe(context))
            this.connectInnerPipe(BProducablePipe(context))
            this.connectInnerPipe(BTurnPipe(context))
        }
    }

    protected open fun installBaseTriggers(context: BGameContext) {
        this.getLocationPlugin().invoke(context)
    }

    protected open fun installPlayers(context: BGameContext) {
        val storage = context.storage
        this.getPlayers(context).forEach { player ->
            storage.putObject(player)
        }
    }

    /**
     * Initializes active player list by scenario.
     */

    protected open fun installPlayerController(context: BGameContext) {
        val playerHeap = context.storage.getHeap(BPlayerHeap::class.java)
        val players = playerHeap.getObjectList()
        val startPosition = this.getStartTurnPlayerPosition()
        context.playerController.apply {
            this.currentPlayerPosition = startPosition
            this.currentPlayerId = players[startPosition].playerId
            this.playerIds = players
                .map { player -> player.playerId }
                .toMutableList()
        }
    }

    protected open fun installUnits(context: BGameContext) {
        val storage = context.storage
        this.getUnits(context).forEach { unit ->
            storage.putObject(unit)
        }
    }

    /**
     * Initializes a map by ui unit ids.
     */

    protected open fun installMapController(context: BGameContext) {
        val controller = context.mapController
        val unitHeap = context.storage.getHeap(BUnitHeap::class.java)
        //Fill map by unit ids:
        val units = unitHeap.getObjectList()
        units.forEach { unit -> controller.notifyUnitChanged(unit) }
        //Check initialized map:
        BMapController.foreach { x, y ->
            val unitId = controller[x, y]
            val isNotInitiablizedField = unitId == BMapController.NOT_ID
            if (isNotInitiablizedField) {
                throw IllegalStateException("Position x: $x y: $y is not initialized")
            }
        }
    }

    protected abstract fun getPlayers(context: BGameContext): Set<BPlayer>

    protected abstract fun getUnits(context: BGameContext): Set<BUnit>

    protected abstract fun getStartTurnPlayerPosition(): Int

    protected abstract fun getLocationPlugin(): BLocationPlugin
}