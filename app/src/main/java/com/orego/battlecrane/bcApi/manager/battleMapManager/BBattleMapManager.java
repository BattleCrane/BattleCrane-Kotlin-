package com.orego.battlecrane.bcApi.manager.battleMapManager;

import com.orego.battlecrane.bcApi.manager.battleMapManager.cell.BCell;
import com.orego.battlecrane.bcApi.scenario.BGameScenario;
import com.orego.battlecrane.bcApi.unit.BUnit;

import java.util.HashMap;
import java.util.Map;

public final class BBattleMapManager {

    public static final int MAP_SIDE = 16;

    private static final int MAP_RANGE = MAP_SIDE * MAP_SIDE;

    private final BCell[][] cells = new BCell[MAP_SIDE][MAP_SIDE];

    private final Map<Integer, BUnit> unitHeap = new HashMap<>();

    private final BMapHolder mapHolder = new BMapHolder();

    public final class BMapHolder {

        //TODO: Handling errors: cell was attached, index out bound etc:
        public final void bindUnitTo(final BUnit unit, final int x, final int y) {
            final int horizontalSide = unit.getHorizontalSide();
            final int verticalSide = unit.getVerticalSide();
            final BCell pivot = BBattleMapManager.this.cells[x][y];
            //Attach pivot to unit:
            unit.setPivot(pivot);
            //Attach unit to cells:
            for (int i = x; i < horizontalSide; i++) {
                for (int j = y; j < verticalSide; j++) {
                    final BCell cell = BBattleMapManager.this.cells[i][j];
                    cell.setAttachedUnit(unit);
                }
            }
            //Put in heap:
            final int unitId = unit.getId();
            BBattleMapManager.this.unitHeap.put(unitId, unit);
        }

        //TODO: Unbind.
    }

    public BBattleMapManager(final BGameScenario initializer) {
        for (int i = 0; i < MAP_SIDE; i++) {
            for (int j = 0; j < MAP_SIDE; j++) {
                final BCell cell = new BCell(i, j);
                this.cells[i][j] = cell;
            }
        }
        initializer.initMap(this.mapHolder);
    }

    /**
     * Boilerplate. TODO:Go to Lombok
     */

    public final Map<Integer, BUnit> getUnitHeap() {
        return this.unitHeap;
    }

    public final BMapHolder getMapHolder() {
        return this.mapHolder;
    }
}