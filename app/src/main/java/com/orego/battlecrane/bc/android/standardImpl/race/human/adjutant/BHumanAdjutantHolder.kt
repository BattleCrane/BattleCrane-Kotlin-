package com.orego.battlecrane.bc.android.standardImpl.race.human.adjutant

import com.orego.battlecrane.bc.android.api.context.BUiGameContext

class BHumanAdjutantHolder private constructor(uiGameContext: BUiGameContext, override val item: BHumanAdjutant) :
    BAdjutantHolder(uiGameContext, item) {

    open class Builder : BAdjutantHolder.Builder() {

        override fun build(uiGameContext: BUiGameContext, item: BAdjutant) =
            BHumanAdjutantHolder(uiGameContext, item as BHumanAdjutant)
    }
}