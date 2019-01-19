package com.orego.battlecrane.bc.engine.api.scenario

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.attackable.BAttackablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.hitPointable.BHitPointablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.levelable.BLevelablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.producable.BProducablePipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.BTurnPipe
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.unit.BUnitPipe
import com.orego.battlecrane.bc.engine.api.context.storage.heap.implementation.*
import com.orego.battlecrane.bc.engine.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.engine.api.model.player.BPlayer
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit

/**
 * Initializes a game step by step.
 */

abstract class BGameScenario {

    /**
     * 1.) Configures context.
     */

    open fun install(context: BGameContext) {
        context.storage.apply {
            this.addHeap(BPlayerHeap())
            this.addHeap(BAdjutantHeap())
            this.addHeap(BUnitHeap())
            this.addHeap(BAttackableHeap())
            this.addHeap(BLevelableHeap())
            this.addHeap(BHitPointableHeap())
            this.addHeap(BProducableHeap())
        }
        context.pipeline.apply {
            this.connectInnerPipe(BUnitPipe(context))
            this.connectInnerPipe(BAttackablePipe(context))
            this.connectInnerPipe(BHitPointablePipe(context))
            this.connectInnerPipe(BLevelablePipe(context))
            this.connectInnerPipe(BProducablePipe(context))
            this.connectInnerPipe(BTurnPipe(context))
        }
    }

    /**
     * 2.) Fills player table.
     */

    abstract fun getPlayers(context: BGameContext): List<BPlayer>

    /**
     * 3.) Fills adjutant table.
     */

    abstract fun getAdjutants(context: BGameContext): List<BAdjutant>

    /**
     * 4.) Fills unit table when player table is ready.
     */

    abstract fun getUnits(context: BGameContext): List<BUnit>

    /**
     * 5.) Invokes when player table is ready.
     */

    abstract val startTurnPlayerPosition: Int
}