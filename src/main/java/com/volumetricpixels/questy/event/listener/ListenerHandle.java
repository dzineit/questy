/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.event.listener;

import com.volumetricpixels.questy.event.Event;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Wraps a {@link Listener} to direct {@link Event}s its way.
 */
public final class ListenerHandle {
    /**
     * The actual {@link Listener}.
     */
    private final Listener listener;
    /**
     * A {@link Map} of {@link Event} subclasses to {@link Set}s of methods
     * which listen for the event type they are mapped from.
     */
    private final Map<Class<? extends Event>, Set<MethodHandle>> eventHandlers;

    public ListenerHandle(Listener listener) {
        this.listener = listener;
        this.eventHandlers = new HashMap<>();

        for (Method meth : listener.getClass().getDeclaredMethods()) {
            EventHandler eh = meth.getAnnotation(EventHandler.class);
            if (eh == null || meth.getParameterCount() != 1) {
                continue;
            }

            Class<?> parameter = meth.getParameterTypes()[0];
            if (!Event.class.isAssignableFrom(parameter)) {
                continue;
            }

            Class<? extends Event> evtClass = parameter.asSubclass(Event.class);
            try {
                MethodHandle handle = MethodHandles.lookup().unreflect(meth);
                Set<MethodHandle> handlers = eventHandlers.get(evtClass);
                if (handlers == null) {
                    handlers = new HashSet<>();
                    eventHandlers.put(evtClass, handlers);
                }

                handlers.add(handle);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void handle(Event event) {
        Class<? extends Event> clazz = event.getClass();
        Set<MethodHandle> handles = eventHandlers.get(clazz);
        if (handles == null || handles.isEmpty()) {
            return;
        }

        for (MethodHandle handle : handles) {
            try {
                handle.invoke(listener, event);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public Listener getListener() {
        return listener;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ListenerHandle
                && listener == ((ListenerHandle) other).listener;
    }
}
