package com.orego.battlecrane.bc.engine.standardImpl.race.human.adjutant

import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.engine.standardImpl.race.human.BHumanRace

class BHumanAdjutant private constructor(context: BGameContext, playerId: Long) :
    BAdjutant(context, playerId), BHumanRace {

    override var isAble = true

    open class Builder : BAdjutant.Builder() {

        override fun build(context: BGameContext, playerId: Long) = BHumanAdjutant(context, playerId)
    }
}