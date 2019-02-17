package com.orego.battlecrane.bc.engine.api.scenario.plugin.implementation.player

import com.orego.battlecrane.bc.engine.api.scenario.plugin.BPlugin

/**
 * Installs configuration for player.
 */

abstract class BPlayerPlugin(protected val playerId: Long) : BPlugin