package com.orego.battlecrane.bc.api.manager.mapManager;

import com.orego.battlecrane.bc.api.manager.BGameContext;
import com.orego.battlecrane.bc.api.manager.mapManager.cell.BCell;
import com.orego.battlecrane.bc.api.scenario.BGameScenario;
import com.orego.battlecrane.bc.api.model.unit.BUnit;
import kotlin.Unit;

import java.util.HashMap;
import java.util.Map;

public final class BMapManager {

    public static final int MAP_SIZE = 16;

    private static final int MAP_RANGE = MAP_SIZE * MAP_SIZE;

    private final BCell[][] cells = new BCell[MAP_SIZE][MAP_SIZE];

    private final Map<Long, BUnit> unitHeap = new HashMap<>();

    private final BMapHolder mapHolder = new BMapHolder();

    public final class BMapHolder {

        //TODO: MAKE CREATE UNIT:

        public final void bindUnitTo(final BUnit unit, final BCell positon) {
            this.bindUnitTo(unit, positon.getX(), positon.getY());
        }

        //TODO: Handling errors: cell was attached, index out bound etc:
        public final void bindUnitTo(final BUnit unit, final int x, final int y) {
            final int horizontalSide = unit.getHorizontalSide();
            final int verticalSide = unit.getVerticalSide();
            final BCell pivot = BMapManager.this.cells[x][y];
            //Attach pivot to entity:
            unit.setPivot(pivot);
            //Attach entity to cells:
            for (int i = x; i < horizontalSide; i++) {
                for (int j = y; j < verticalSide; j++) {
                    final BCell cell = BMapManager.this.cells[i][j];
                    cell.setAttachedUnit(unit);
                }
            }
            //Put in heap:
            final long unitId = unit.getUnitId();
            BMapManager.this.unitHeap.put(unitId, unit);

            //TODO: SET TO ACTION:
//            final boolean isPlaced = unit.isPlaced(pivot);
//            if (isPlaced) {
//
//                //Notify subscribers:
//                unit.getOnCreateObserver().values().forEach(it -> it.onCreate(unit));
//            }
//            return isPlaced;
        }
        //TODO: Unbind.
    }

    public BMapManager(final BGameScenario initializer, final BGameContext gameContext) {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                final BCell cell = new BCell(i, j);
                this.cells[i][j] = cell;
            }
        }
        initializer.initMap(this.mapHolder, gameContext);
    }

    public final boolean inBounds(final int x, final int y) {
        return x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE;
    }

    public final BUnit getUnitByPosition(final BCell position) {
        return this.getUnitByPosition(position.getX(), position.getY());
    }

    public final BUnit getUnitByPosition(final int x, final int y) {
        return this.cells[x][y].getAttachedUnit();
    }

    /**
     * Boilerplate. TODO:Go to Lombok
     */

    public final Map<Long, BUnit> getUnitHeap() {
        return this.unitHeap;
    }

    public final BMapHolder getMapHolder() {
        return this.mapHolder;
    }
}