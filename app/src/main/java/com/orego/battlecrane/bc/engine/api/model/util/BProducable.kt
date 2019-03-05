package com.orego.battlecrane.bc.engine.api.model.util

/**
 * Defines produce properties.
 */

interface BProducable {

    val producableId : Long

    var playerId : Long

    var isProduceEnable : Boolean
}