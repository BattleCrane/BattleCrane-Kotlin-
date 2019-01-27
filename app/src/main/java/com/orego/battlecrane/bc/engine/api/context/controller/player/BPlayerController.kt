package com.orego.battlecrane.bc.engine.api.context.controller.player

/**
 * Represents a list of active players in the game.
 */

class BPlayerController {

    companion object {

        private const val NOT_INITIALIZED = -1
    }

    var currentPlayerPosition: Int = NOT_INITIALIZED

    var currentPlayerId: Long = NOT_INITIALIZED.toLong()

    lateinit var playerIds: MutableList<Long>
}