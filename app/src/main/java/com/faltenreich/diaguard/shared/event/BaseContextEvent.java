package com.faltenreich.diaguard.shared.event;

/**
 * Created by Faltenreich on 23.03.2016.
 */
public abstract class BaseContextEvent <T> extends BaseEvent {

    public final T context;

    public BaseContextEvent(T context) {
        this.context = context;
    }
}
