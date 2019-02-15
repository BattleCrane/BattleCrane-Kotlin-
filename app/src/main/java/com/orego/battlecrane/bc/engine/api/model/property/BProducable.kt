package com.orego.battlecrane.bc.engine.api.model.property

interface BProducable {

    val producableId : Long

    var playerId : Long

    var isProduceEnable : Boolean
}