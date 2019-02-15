package com.orego.battlecrane.bc.engine.api.util.common

/**
 * List.
 */

//fun <T, R> List<T>.filterMap(
//    filterFunc: (T) -> Boolean,
//    transformFunc: (T) -> R
//) = this.filter { filterFunc(it) }.map { transformFunc(it) }

/**
 * Point.
 */

typealias BPoint = Pair<Int, Int>

val BPoint.x
    get() = this.first

val BPoint.y
    get() = this.second