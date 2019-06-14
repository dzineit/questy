/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.questy.event;

import gnu.trove.set.hash.THashSet;

import com.volumetricpixels.questy.event.Event;
import com.volumetricpixels.questy.event.EventManager;
import com.volumetricpixels.questy.util.Util;

import java.util.Optional;
import java.util.Set;

/**
 * A simple {@link EventManager}, which stores {@link SimpleListenerHandle} objects
 * and sends {@link Event}s their way when a relevant {@link Event} is fired via
 * {@link #fire(Event)}.
 */
public class SimpleEventManager implements EventManager {
    /**
     * All registered {@link Object}s, each contained within a {@link
     * SimpleListenerHandle} object.
     */
    private final Set<SimpleListenerHandle> listeners;

    /**
     * Constructs a new EventManager with no registered {@link Object}s.
     */
    public SimpleEventManager() {
        listeners = new THashSet<>();
    }

    /**
     * {@inheritDoc}
     */
    public boolean register(Object listener) {
        return listeners.add(new SimpleListenerHandle(listener));
    }

    /**
     * {@inheritDoc}
     */
    public void unregister(Object listener) {
        listeners.removeIf(handle -> handle.getListener() == listener);
    }

    /**
     * {@inheritDoc}
     */
    public <T extends Event> T fire(T event) {
        listeners.stream()
                .map(l -> l.handle(event))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(Util::notEmpty)
                .forEach(listenerSet ->
                        listenerSet.forEach(listener -> {
                            try {
                                listener.invoke(event);
                            } catch (Throwable throwable) {
                                throw new RuntimeException(throwable);
                            }
                        }));

        return event;
    }
}
