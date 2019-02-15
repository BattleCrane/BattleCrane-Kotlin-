package com.orego.battlecrane.bc.engine.api.context.controller.player

/**
 * Represents a list of active players in the game.
 */

class BPlayerController {

    companion object {

        private const val NOT_INIT_ID = -1
    }

    var currentPlayerPosition: Int = NOT_INIT_ID

    var currentPlayerId: Long = NOT_INIT_ID.toLong()

    lateinit var playerIds: MutableList<Long>
}