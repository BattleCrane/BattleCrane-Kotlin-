package com.orego.battlecrane.bc.api.context.mapManager;

import com.orego.battlecrane.bc.api.context.BGameContext;
import com.orego.battlecrane.bc.api.context.mapManager.point.BPoint;
import com.orego.battlecrane.bc.api.context.pipeline.BEvent;
import com.orego.battlecrane.bc.api.context.pipeline.BEventContract;
import com.orego.battlecrane.bc.api.context.pipeline.BEventKt;
import com.orego.battlecrane.bc.api.context.pipeline.BPipeline;
import com.orego.battlecrane.bc.api.model.action.BAction;
import com.orego.battlecrane.bc.api.model.unit.BUnit;
import com.orego.battlecrane.bc.api.scenario.BGameScenario;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class BMapManager {

    public static final int MAP_SIZE = 16;

    private final BPoint[][] matrix = new BPoint[MAP_SIZE][MAP_SIZE];

    private final Map<Long, BUnit> unitHeap = new HashMap<>();

    private final BMapHolder mapHolder = new BMapHolder();

    private final BPipeline pipeline;

    public BMapManager(final BGameScenario scenario, final BGameContext context) {
        this.pipeline = context.getPipeline();
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                final BPoint cell = new BPoint(i, j);
                this.matrix[i][j] = cell;
            }
        }
        scenario.initMap(this.mapHolder, context);
    }

    public final boolean createUnit(final BUnit unit, final BPoint pivot) {
        final int startX = pivot.getX();
        final int startY = pivot.getY();
        final int endX = startX + unit.getHorizontalSize();
        final int endY = startX + unit.getVerticalSize();
        if (this.inBounds(startX, startY)
                && this.inBounds(startX, endY)
                && this.inBounds(endX, startY)
                && this.inBounds(endX, endY)) {
            if (unit.isPlaced(pivot)) {
                //Put in pipeline:
                final BEvent event = new BEvent(BEventContract.CREATE, unit);
                this.pipeline.handle(event);
                if (BEventKt.isValidEvent(event)
                        && Objects.equals(event.getName(), BEventContract.CREATE)
                        && event.getAny() instanceof BAction) {
                    //Attach unit on matrix:
                    this.mapHolder.bindUnitTo(unit, startX, startY);
                    //Launch lifecycle:
                    unit.onCreate();
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean inBounds(final BPoint point) {
        return this.inBounds(point.getX(), point.getY());
    }

    public final boolean inBounds(final int x, final int y) {
        return x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE;
    }

    public final BUnit getUnitByPosition(final BPoint position) {
        return this.getUnitByPosition(position.getX(), position.getY());
    }

    public final BUnit getUnitByPosition(final int x, final int y) {
        return this.matrix[x][y].getAttachedUnit();
    }

    public final class BMapHolder {

        //TODO: Handling errors: cell was attached, index out bound etc:
        public final void bindUnitTo(final BUnit unit, final int x, final int y) {
            final int horizontalSide = unit.getHorizontalSize();
            final int verticalSide = unit.getVerticalSize();
            final BPoint pivot = BMapManager.this.matrix[x][y];
            //Attach pivot to entity:
            unit.setPivot(pivot);
            //Attach entity to matrix:
            for (int i = x; i < horizontalSide; i++) {
                for (int j = y; j < verticalSide; j++) {
                    final BPoint point = BMapManager.this.matrix[i][j];
                    point.setAttachedUnit(unit);
                }
            }
            //Put in heap:
            final long unitId = unit.getUnitId();
            BMapManager.this.unitHeap.put(unitId, unit);
        }

        //TODO: Unbind.

        public final boolean hasBoundUnit(final int x, final int y) {
            return BMapManager.this.matrix[x][y].getAttachedUnit() != null;
        }
    }

    /**
     * Boilerplate. TODO:Go to Lombok
     */

    public final Map<Long, BUnit> getUnitHeap() {
        return this.unitHeap;
    }
}