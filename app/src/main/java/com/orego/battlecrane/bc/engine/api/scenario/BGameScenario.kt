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
import com.orego.battlecrane.bc.engine.api.model.property.BAttackable
import com.orego.battlecrane.bc.engine.api.model.property.BHitPointable
import com.orego.battlecrane.bc.engine.api.model.property.BLevelable
import com.orego.battlecrane.bc.engine.api.model.property.BProducable
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.api.scenario.plugin.implementation.location.BLocationPlugin
import com.orego.battlecrane.bc.engine.api.scenario.plugin.implementation.race.BRacePlugin

/**
 * Initializes a game step by step.
 */

abstract class BGameScenario {

    /**
     * 1.) Configures context.
     */

    open fun install(context: BGameContext) {
        this.installContextGenerator(context)
        this.installStorage(context)
        this.installPipeline(context)
        this.installBaseTriggers(context)
        this.installPlayers(context)
        this.installPlayerController(context)
        this.installPlugins(context)
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
            this.addHeap(BPlayerHeap())
            this.addHeap(BUnitHeap())
            this.addHeap(BAttackableHeap())
            this.addHeap(BLevelableHeap())
            this.addHeap(BHitPointableHeap())
            this.addHeap(BProducableHeap())
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

    }

    protected open fun installPlayers(context: BGameContext) {
        val storage = context.storage
        this.getPlayers(context).forEach { player ->
            storage.addObject(player)
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

    protected open fun installPlugins(context: BGameContext) {
        this.getLocationPlugin(context).install(context)
        this.getRacePlugins(context).forEach { plugin ->
            plugin.install(context)
        }
    }

    protected open fun installUnits(context: BGameContext) {
        val storage = context.storage
        this.getUnits(context).forEach { unit ->
            storage.addObject(unit)
        }
    }

    /**
     * Initializes a map by unit ids.
     */

    protected open fun installMapController(context: BGameContext) {
        val controller = context.mapController
        context.storage.getHeap(BUnitHeap::class.java).getObjectList().forEach { unit ->
            controller.notifyUnitChanged(unit)
        }
        //Check initialized map:
        BMapController.foreach { x, y ->
            val isNotInitiablizedField = controller[x, y] == BMapController.NOT_ID
            if (isNotInitiablizedField) {
                throw IllegalStateException("Position x: $x y: $y is not initialized")
            }
        }
    }

    protected abstract fun getPlayers(context: BGameContext): List<BPlayer>

    protected abstract fun getRacePlugins(context: BGameContext): List<BRacePlugin>

    protected abstract fun getLocationPlugin(context: BGameContext): BLocationPlugin

    protected abstract fun getUnits(context: BGameContext): List<BUnit>

    protected abstract fun getStartTurnPlayerPosition(): Int
}