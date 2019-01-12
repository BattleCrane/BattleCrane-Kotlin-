package com.orego.battlecrane.bc.std.scenario.skirmish.model.player

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.player.BPlayer

class BStandardSkirmishPlayerBuilder : BPlayer.Builder() {

    //TODO: ADD TIMER NODE!!!
    override fun build(context: BGameContext): BPlayer {
        return super.build(context)
    }
}