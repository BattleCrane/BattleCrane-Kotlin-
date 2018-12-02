package bcApi.race.human.buildings

import bcApi.manager.BGameManager
import bcApi.unit.BUnit
import bcApi.unit.contract.BHealthable

class BHumanWall(gameManager: BGameManager) : BUnit(gameManager), BHumanBuilding, BHealthable {

    companion object {

        private const val DEFAULT_VERTICAL_SIDE = 1

        private const val DEFAULT_HORIZONTAL_SIDE = 2

        private const val DEFAULT_MAX_HEALTH = 1
    }

    /**
     * Properties.
     */

    override val verticalSide = DEFAULT_VERTICAL_SIDE

    override val horizontalSide = DEFAULT_HORIZONTAL_SIDE

    override var currentHealth = DEFAULT_MAX_HEALTH

    override var maxHealth = DEFAULT_MAX_HEALTH

    /**
     * Observers.
     */

    override val decreaseHealthObserver: MutableMap<Long, BHealthable.HealthListener> = mutableMapOf()

    override val increaseHealthObserver: MutableMap<Long, BHealthable.HealthListener> = mutableMapOf()
}