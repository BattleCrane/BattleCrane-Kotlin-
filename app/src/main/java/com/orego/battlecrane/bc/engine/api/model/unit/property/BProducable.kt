package com.orego.battlecrane.bc.engine.api.model.unit.property

/**
 * Defines produce properties.
 */

interface BProducable {

    val producableId : Long

    var playerId : Long

    var isProduceEnable : Boolean
}