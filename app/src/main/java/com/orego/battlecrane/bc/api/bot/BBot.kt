package com.orego.battlecrane.bc.api.bot

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent

interface BBot {

    /**
     * Calculates turn.
     */

    fun calc(context: BGameContext): List<BEvent>
}