package com.orego.battlecrane.bc.api.context.generator

import com.orego.battlecrane.bc.api.context.pipeline.model.node.BNode
import com.orego.battlecrane.bc.api.context.pipeline.model.pipe.BPipe
import com.orego.battlecrane.bc.api.model.adjutant.BAdjutant
import com.orego.battlecrane.bc.api.model.unit.BUnit
import com.orego.battlecrane.bc.api.model.property.attackable.BAttackable
import com.orego.battlecrane.bc.api.model.property.hitPointable.BHitPointable
import com.orego.battlecrane.bc.api.model.property.levelable.BLevelable
import com.orego.battlecrane.bc.api.model.property.producable.BProducable
import com.orego.battlecrane.bc.api.model.player.BPlayer

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