package com.orego.battlecrane.bc.android.scenario.skirmish

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.scenario.BUiGameScenario
import com.orego.battlecrane.bc.android.api.scenario.plugin.implementation.race.BUiRacePlugin
import com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.BUiSkirmishGrassUiLocationPlugin
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.engine.scenario.skirmish.BSkirmishScenario

class BUiSkirmishScenario : BUiGameScenario() {

    override val gameScenario = BSkirmishScenario()

    override val uiRacePlugins: Map<Class<out BUiRacePlugin>, BUiRacePlugin> = mutableMapOf(
        BUiUiSkirmishHumanPlugin::class.java to BUiUiSkirmishHumanPlugin()
    )

    override val uiLocationPlugin = BUiSkirmishGrassUiLocationPlugin()

    override fun install(uiGameContext: BUiGameContext) {
        super.install(uiGameContext)
        //Configure end turn button:
        uiGameContext.uiProvider.endTurnConstraintLayout.setOnClickListener {
            this.endTurn(uiGameContext.gameContext)
        }
    }

    private fun endTurn(gameContext: BGameContext) {
        gameContext.pipeline.broacastEvent(BOnTurnFinishedPipe.Event(gameContext.playerController.currentPlayerId))
    }
}