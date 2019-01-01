package com.orego.battlecrane.bc.std.race.human.adjutant

import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.std.race.human.BHumanRace

class BHumanAdjutant(playerId : Long) : BAdjutant(playerId), BHumanRace {

    /**
     * Builder.
     */

    class Builder : BAdjutant.Builder() {

        override fun build(playerId: Long) = BHumanAdjutant(playerId)
    }
}