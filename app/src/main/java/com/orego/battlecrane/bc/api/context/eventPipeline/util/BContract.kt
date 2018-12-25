package com.orego.battlecrane.bc.api.context.eventPipeline.util

import com.orego.battlecrane.bc.api.context.eventPipeline.model.BEvent

object EventUtil {

    fun isValid(event: BEvent?): Boolean {
        return event?.name != null && event.bundle != null
    }
}