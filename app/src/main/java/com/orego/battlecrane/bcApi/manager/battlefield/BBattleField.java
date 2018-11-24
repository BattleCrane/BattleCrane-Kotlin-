package com.orego.battlecrane.bcApi.manager.battlefield;

import com.orego.battlecrane.bcApi.manager.battlefield.cell.BCell;
import com.orego.battlecrane.bcApi.scenario.BGameScenario;
import com.orego.battlecrane.bcApi.manager.unit.BUnit;

import java.util.HashMap;
import java.util.Map;

public final class BBattleField {

    public static final int FIELD_SIDE = 16;

    private static final int FIELD_RANGE = FIELD_SIDE * FIELD_SIDE;

    private final BCell[][] cells = new BCell[FIELD_SIDE][FIELD_SIDE];

    private final Map<Integer, BUnit> unitHeap = new HashMap<>();

    public BBattleField(final BGameScenario initializer) {
        initializer.initMap(this.cells, this.unitHeap);
    }
}