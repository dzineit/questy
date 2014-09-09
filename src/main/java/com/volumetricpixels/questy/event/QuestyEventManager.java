/*
 * This file is part of Questy, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 VolumetricPixels <http://volumetricpixels.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
import java.util.Map.Entry;
import java.util.Set;

/**
 * The event manager used for {@link QuestListener}s in Questy. To be honest, I
 * have no idea why I decided to use this new {@link MethodHandle} system, and
 * I'm even less convinced that it will work.
 */
public class QuestyEventManager {
    private static final Map<Class<? extends QuestyEvent>, MethodHandle> lookup;
    private static final Class<QuestListener> listenerCls = QuestListener.class;

    static {
        Map<Class, String> temp = new HashMap<>();
        temp.put(QuestStartEvent.class, "questStarted");
        temp.put(QuestCompleteEvent.class, "questCompleted");
        temp.put(QuestAbandonEvent.class, "questAbandoned");
        temp.put(ObjectiveFailEvent.class, "objectiveFailed");
        temp.put(ObjectiveCompleteEvent.class, "objectiveCompleted");
        temp.put(ObjectiveStartEvent.class, "objectiveStarted");

        lookup = new HashMap<>();

        try {
            for (Entry<Class, String> entry : temp.entrySet()) {
                Class cls = entry.getKey();
                String meth = entry.getValue();
                lookup.put(cls, MethodHandles.lookup().findVirtual(listenerCls,
                        meth, MethodType.methodType(void.class, cls)));
            }
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
