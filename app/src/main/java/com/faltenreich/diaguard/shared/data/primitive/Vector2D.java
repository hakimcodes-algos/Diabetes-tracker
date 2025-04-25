package com.faltenreich.diaguard.shared.data.primitive;

import androidx.annotation.NonNull;

public class Vector2D {

    public final int x;
    public final int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%d, %d", x, y);
    }
}
