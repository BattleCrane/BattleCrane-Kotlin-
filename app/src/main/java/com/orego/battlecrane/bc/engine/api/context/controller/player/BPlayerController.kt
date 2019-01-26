package com.orego.battlecrane.bc.engine.api.context.controller.player

import com.orego.battlecrane.bc.engine.api.scenario.BGameScenario

/**
 * Represents a list of active players in the game.
 */

class BPlayerController {

    companion object {

        private const val NOT_INITIALIZED = -1
    }

    var currentPlayerPosition: Int = NOT_INITIALIZED

    var currentPlayerId: Long = NOT_INITIALIZED.toLong()

    lateinit var ablePlayers: MutableList<Long>

    /**
     * Initializes active player list by scenario.
     */

    fun install(scenario: BGameScenario) {

    }
}