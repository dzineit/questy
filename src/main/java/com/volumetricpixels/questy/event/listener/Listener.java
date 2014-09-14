/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.event.listener;

/**
 * Listens for events. In implementations each listener method should be
 * declared {@code public}, and should follow the following syntax. There should
 * always be only one parameter for the method, and it should be something which
 * extends Event (i.e QuestStartEvent).
 *
 * <code>
 *     @EventHandler
 *     public void anyMethodName(ListenedEvent anyFieldName) {
 *     }
 * </code>
 *
 * Listener implementations should be registered via {@link
 * com.volumetricpixels.questy.event.EventManager#register(Listener)} if they
 * want to receive events.
 */
public interface Listener {
}
