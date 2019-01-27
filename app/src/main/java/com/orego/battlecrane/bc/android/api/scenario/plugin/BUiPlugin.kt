package com.orego.battlecrane.bc.android.api.scenario.plugin

import com.orego.battlecrane.bc.android.api.context.BUiGameContext

interface BUiPlugin {

    fun install(uiGameContext: BUiGameContext)
}