package com.orego.battlecrane.bc.engine.api.context.generator

/**
 * Keeps type identiificator generators.
 */

class BContextGenerator {

    val generatorMap = mutableMapOf<Class<*>, IdGenerator>()

    fun getIdGenerator(clazz: Class<*>) = this.generatorMap[clazz]!!

    class IdGenerator(private var counter: Long) {

        fun generateId() = this.counter++
    }
}