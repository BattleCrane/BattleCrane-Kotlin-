package com.orego.battlecrane.bc.engine.api.util.point

/**
 * Point.
 */

typealias BPoint = Pair<Int, Int>

val BPoint.x
    get() = this.first

val BPoint.y
    get() = this.second