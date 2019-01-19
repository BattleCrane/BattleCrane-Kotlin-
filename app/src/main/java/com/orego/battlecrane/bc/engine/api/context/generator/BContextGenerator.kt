package com.orego.battlecrane.bc.engine.api.context.generator

import com.orego.battlecrane.bc.engine.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.engine.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.engine.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.engine.api.model.unit.BUnit
import com.orego.battlecrane.bc.engine.api.model.property.BAttackable
import com.orego.battlecrane.bc.engine.api.model.property.BHitPointable
import com.orego.battlecrane.bc.engine.api.model.property.BLevelable
import com.orego.battlecrane.bc.engine.api.model.property.BProducable
import com.orego.battlecrane.bc.engine.api.model.player.BPlayer

class BContextGenerator {

    val generatorMap = mutableMapOf<Class<*>, IdGenerator>(
        BPipe::class.java to IdGenerator(0),
        BNode::class.java to IdGenerator(0),
        BPlayer::class.java to IdGenerator(1),
        BAdjutant::class.java to IdGenerator(1),
        BUnit::class.java to IdGenerator(0),
        BAttackable::class.java to IdGenerator(0),
        BHitPointable::class.java to IdGenerator(0),
        BLevelable::class.java to IdGenerator(0),
        BProducable::class.java to IdGenerator(0)
    )

    fun getIdGenerator(clazz: Class<*>) = this.generatorMap[clazz]!!

    class IdGenerator(private var counter: Long) {

        fun generateId() = this.counter++
    }
}