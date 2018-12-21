package com.orego.battlecrane.bc.api.context.eventPipeline

object BContract {

    object Event {

        const val CREATE = "CREATE"

        fun isValid(event: BEvent?): Boolean {
            return event?.name != null && event.any != null
        }
    }
}

