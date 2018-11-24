package com.orego.battlecrane.bcApi.manager.field;

import android.annotation.SuppressLint;
import com.orego.battlecrane.bcApi.unit.BUnit;
import com.orego.battlecrane.bcApi.unit.field.BField;

import java.util.HashMap;
import java.util.Map;

public final class BBattleField {
    
    public static final int FIELD_SIDE = 16;

    private static final int FIELD_RANGE = FIELD_SIDE * FIELD_SIDE;
    
    private final BCell[][] cells = new BCell[FIELD_SIDE][FIELD_SIDE];

    @SuppressLint("UseSparseArrays")
    private final Map<Integer, BUnit> unitHeap = new HashMap<>();

    public BBattleField() {
        for (int i = 0; i < FIELD_SIDE; i++) {
            for (int j = 0; j < FIELD_SIDE; j++) {
                this.field[i][j] = new BField();
            }
        }
    }

    public interface BInitMapAlgorithm {

        public void fill(final BCell[] cells, )
    }
}