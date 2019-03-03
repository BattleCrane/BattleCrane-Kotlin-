package com.orego.battlecrane.bc.engine.scenario.skirmish.model.player

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.player.BPlayer

class BSkirmishPlayerBuilder : BPlayer.Builder() {

    //TODO: ADD TIMER NODE!!!
    override fun onCreate(context: BGameContext): BPlayer {
        return super.onCreate(context)
    }
}