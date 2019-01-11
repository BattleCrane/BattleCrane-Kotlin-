package com.orego.battlecrane.ui.model.std.race.human.adjutant

import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.std.race.human.adjutant.BHumanAdjutant
import com.orego.battlecrane.ui.model.api.context.BUiGameContext
import com.orego.battlecrane.ui.model.api.holder.adjutant.BAdjutantHolder

class BHumanAdjutantHolder(uiGameContext: BUiGameContext, override val item: BHumanAdjutant) :
    BAdjutantHolder(uiGameContext, item) {

    class Builder : BAdjutantHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BAdjutant) =
            BHumanAdjutantHolder(uiGameContext, item as BHumanAdjutant)
    }
}