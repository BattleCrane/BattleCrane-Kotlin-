package com.orego.battlecrane.bc.engine.scenario.skirmish.model.player

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.player.BPlayer

class BStandardSkirmishPlayerBuilder : BPlayer.Builder() {

    //TODO: ADD TIMER NODE!!!
    override fun build(context: BGameContext): BPlayer {
        return super.build(context)
    }
}