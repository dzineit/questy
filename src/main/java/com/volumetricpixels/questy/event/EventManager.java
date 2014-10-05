/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.event;

import gnu.trove.set.hash.THashSet;

import java.util.Iterator;
import java.util.Set;

/**
 * A simple {@link EventManager}, which stores {@link ListenerHandle} objects
 * and sends {@link Event}s their way when a relevant {@link Event} is fired via
 * {@link #fire(Event)}.
 */
public class EventManager {
    /**
     * All registered {@link Object}s, each contained within a {@link
     * ListenerHandle} object.
     */
    private final Set<ListenerHandle> listeners;

    /**
     * Constructs a new EventManager with no registered {@link Object}s.
     */
    public EventManager() {
        listeners = new THashSet<>();
    }

    /**
     * Registers the given {@link Object} to this {@link EventManager}. Logic
     * for registration of {@code @EventHandler} methods can be found in {@link
     * ListenerHandle#ListenerHandle(Object)}.
     *
     * @param listener the {@link Object} to register
     * @return whether the given {@link Object} was successfully registered
     */
    public boolean register(Object listener) {
        return listeners.add(new ListenerHandle(listener));
    }

    /**
     * Unregisters all {@link ListenerHandle}s which wrap the given {@link
     * Object}.
     *
     * @param listener the {@link Object} to unregister
     */
    public void unregister(Object listener) {
        Iterator<ListenerHandle> it = listeners.iterator();
        while (it.hasNext()) {
            ListenerHandle handle = it.next();
            if (handle.getListener() == listener) {
                it.remove();
            }
        }
    }

    /**
     * Fires the given {@link Event}, directing it to all registered {@link
     * Object}s which are subscribed to the given event type.
     *
     * @param event the {@link Event} to fire
     * @param <T> the type of {@link Event} to fire, and therefore return
     * @return the given {@link Event}
     */
    public <T extends Event> T fire(T event) {
        listeners.forEach((listener) -> listener.handle(event));
        return event;
    }
}
