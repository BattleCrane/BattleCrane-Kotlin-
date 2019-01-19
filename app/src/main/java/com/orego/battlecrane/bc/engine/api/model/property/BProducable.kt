package com.orego.battlecrane.bc.engine.api.model.property

interface BProducable {

    var playerId : Long

    val producableId : Long

    var isProduceEnable : Boolean
}