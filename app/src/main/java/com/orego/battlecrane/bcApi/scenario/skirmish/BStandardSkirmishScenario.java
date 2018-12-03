package com.orego.battlecrane.bcApi.scenario.skirmish;

import com.orego.battlecrane.bcApi.manager.mapManager.BMapManager;
import com.orego.battlecrane.bcApi.scenario.BGameScenario;
import com.orego.battlecrane.bcApi.unit.BUnit;
import com.orego.battlecrane.bcApi.unit.field.empty.BEmptyField;

import static com.orego.battlecrane.bcApi.manager.mapManager.BMapManager.MAP_SIZE;

public final class BStandardSkirmishScenario implements BGameScenario {

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