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

        object Building {

            const val PATH = "${Action.PATH}/building"
        }

        object Infantry {

            const val PATH = "${Action.PATH}/infantry"
        }

        object Vehicle {

            const val PATH = "${Action.PATH}/vehicle"
        }

        object Common {

            const val PATH = "${Action.PATH}/common"
        }
    }


    object Build {

        const val BARRACKS = "race/human/action/barracks.png"

        const val FACTORY = "race/human/action/factory.png"

        const val GENERATOR = "race/human/action/generator.png"

        const val TURRET = "race/human/action/turret.png"

        const val WALL = "race/human/action/wall.png"
    }

    object Upgrade {

        const val BUILDING = "race/human/action/upgrade_building.png"
    }

    object Train {

        const val MARINE = "race/human/action/train_marine_lvl_1.png"
    }

    object Produce {

        const val TANK = "race/human/action/train_tank_lvl_1.png"
    }
}