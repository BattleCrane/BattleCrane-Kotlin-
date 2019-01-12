package com.orego.battlecrane.bc.std.race.human.adjutant

import com.orego.battlecrane.bc.api.context.BGameContext
import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.std.race.human.BHumanRace

class BHumanAdjutant private constructor(context: BGameContext, playerId: Long) :
    BAdjutant(context, playerId), BHumanRace {

    override var isAble = true

    open class Builder : BAdjutant.Builder() {

        override fun build(context: BGameContext, playerId: Long) = BHumanAdjutant(context, playerId)
    }
}