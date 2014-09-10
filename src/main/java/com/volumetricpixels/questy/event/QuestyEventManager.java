/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.event;

import com.volumetricpixels.questy.QuestManager;
import com.volumetricpixels.questy.event.listener.QuestListener;
import com.volumetricpixels.questy.event.quest.QuestAbandonEvent;
import com.volumetricpixels.questy.event.quest.QuestCompleteEvent;
import com.volumetricpixels.questy.event.quest.QuestStartEvent;
import com.volumetricpixels.questy.event.quest.objective.ObjectiveCompleteEvent;
import com.volumetricpixels.questy.event.quest.objective.ObjectiveFailEvent;
import com.volumetricpixels.questy.event.quest.objective.ObjectiveStartEvent;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The event manager used for {@link QuestListener}s in Questy.
 */
public class QuestyEventManager {
    private static final Map<Class, MethodHandle> lookup = new HashMap<>();
    private static final Class<QuestListener> listenerCls = QuestListener.class;

    static {
        register(QuestStartEvent.class, "questStarted");
        register(QuestCompleteEvent.class, "questCompleted");
        register(QuestAbandonEvent.class, "questAbandoned");
        register(ObjectiveFailEvent.class, "objectiveFailed");
        register(ObjectiveCompleteEvent.class, "objectiveCompleted");
        register(ObjectiveStartEvent.class, "objectiveStarted");
    }

    private static void register(Class cls, String meth) {
        try {
            lookup.put(cls, MethodHandles.lookup().findVirtual(listenerCls,
                    meth, MethodType.methodType(void.class, cls)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final QuestManager questManager;
    private final Set<QuestListener> listeners;

    public QuestyEventManager(QuestManager questManager) {
        this.questManager = questManager;
        this.listeners = new HashSet<>();
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public boolean subscribe(QuestListener listener) {
        return listeners.add(listener);
    }

    public boolean unsubscribe(QuestListener listener) {
        return listeners.remove(listener);
    }

    public <T extends QuestyEvent> T fire(T event) {
        for (QuestListener listener : listeners) {
            try {
                lookup.get(event.getClass()).invoke(listener, event);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return event;
    }
}
