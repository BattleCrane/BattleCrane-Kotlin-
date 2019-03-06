package com.orego.battlecrane.bc.android.standardImpl.race.human.asset

object BUiHumanAssets {

    const val PATH = "race/human"

    object Unit {

        const val PATH = "${BUiHumanAssets.PATH}/unit"

        object Building {

            const val PATH = "${Unit.PATH}/building"
        }

        object Infantry {

            const val PATH = "${Unit.PATH}/infantry"
        }

        object Vehicle {

            const val PATH = "${Unit.PATH}/vehicle"
        }
    }

    object Action {

        const val PATH = "${BUiHumanAssets.PATH}/action"

        object Attack {

            const val PATH = "${Action.PATH}/attack"
        }

        object Build {

            const val PATH = "${Action.PATH}/build"
        }

        object Train {

            const val PATH = "${Action.PATH}/train"
        }

        object Produce {

            const val PATH = "${Action.PATH}/produce"
        }

        object Upgrate {

            const val PATH = "${Action.PATH}/upgrade"
        }
    }
}