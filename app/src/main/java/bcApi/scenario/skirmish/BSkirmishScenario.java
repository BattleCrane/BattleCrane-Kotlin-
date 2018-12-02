package bcApi.scenario.skirmish;

import bcApi.manager.mapManager.BMapManager;
import bcApi.unit.BUnit;
import bcApi.unit.field.empty.BEmptyField;
import bcApi.scenario.BGameScenario;

import static bcApi.manager.mapManager.BMapManager.MAP_SIZE;

public final class BSkirmishScenario implements BGameScenario {

    @Override
    public final void initMap(final BMapManager.BMapHolder mapHolder) {
        for (int x = 0; x < MAP_SIZE; x++) {
            for (int y = 0; y < MAP_SIZE; y++) {
                final BUnit emptyField = new BEmptyField();
                mapHolder.bindUnitTo(emptyField, x, y);
            }
        }
    }
}