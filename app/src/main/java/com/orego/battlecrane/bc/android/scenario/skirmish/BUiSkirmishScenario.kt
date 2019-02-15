package com.orego.battlecrane.bc.android.scenario.skirmish

import com.orego.battlecrane.bc.android.api.context.BUiGameContext
import com.orego.battlecrane.bc.android.api.scenario.BUiGameScenario
import com.orego.battlecrane.bc.android.api.scenario.plugin.implementation.race.BUiRacePlugin
import com.orego.battlecrane.bc.android.scenario.skirmish.model.location.grass.BUiSkirmishGrassUiLocationPlugin
import com.orego.battlecrane.bc.android.scenario.skirmish.model.race.human.BUiSkirmishHumanPlugin
import com.orego.battlecrane.bc.engine.api.context.BGameContext
import com.orego.battlecrane.bc.engine.api.context.pipeline.implementation.turn.node.pipe.onTurnFinished.BOnTurnFinishedPipe
import com.orego.battlecrane.bc.engine.scenario.skirmish.BSkirmishScenario

/**
 * This is beta skirmish scenario:
 * Mode: 1x1;
 * Race: Human.
 */

class BUiSkirmishScenario : BUiGameScenario() {

    companion object {

        private const val FIRST_PLAYER_ID: Long = 1

        private const val SECOND_PLAYER_ID: Long = 2
    }

    private val uiRacePluginList = mutableListOf(
        BUiSkirmishHumanPlugin(FIRST_PLAYER_ID),
        BUiSkirmishHumanPlugin(SECOND_PLAYER_ID)
    )

    override fun getGameScenario() = BSkirmishScenario()

    override fun getUiRacePlugins() = this.uiRacePluginList

    override fun getUiLocationPlugin() = BUiSkirmishGrassUiLocationPlugin()

    override fun install(uiGameContext: BUiGameContext) {
        super.install(uiGameContext)
        Installer.install(uiGameContext)
    }

    private object Installer {

        fun install(uiGameContext: BUiGameContext) {
            this.installEndTurnButton(uiGameContext)
        }

        private fun installEndTurnButton(uiGameContext: BUiGameContext) {
            val gameContext = uiGameContext.gameContext
            uiGameContext.uiProvider.endTurnConstraintLayout.setOnClickListener {
                val currentPlayer = gameContext.playerController.currentPlayerId
                val endTurnEvent = BOnTurnFinishedPipe.Event(currentPlayer)
                gameContext.pipeline.broacastEvent(endTurnEvent)
            }
        }
    }
}