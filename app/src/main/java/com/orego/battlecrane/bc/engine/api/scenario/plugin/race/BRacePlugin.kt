package com.orego.battlecrane.bc.engine.api.scenario.plugin.race

import com.orego.battlecrane.bc.engine.api.scenario.plugin.BPlugin

/**
 * Installs configuration for player.
 */

abstract class BRacePlugin(protected val playerId: Long) : BPlugin