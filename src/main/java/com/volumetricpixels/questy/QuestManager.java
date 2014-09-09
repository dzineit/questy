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
package com.volumetricpixels.questy;

import com.volumetricpixels.questy.event.QuestyEventManager;
import com.volumetricpixels.questy.event.quest.QuestAbandonEvent;
import com.volumetricpixels.questy.event.quest.QuestCompleteEvent;
import com.volumetricpixels.questy.event.quest.QuestStartEvent;
import com.volumetricpixels.questy.loading.QuestLoader;
import com.volumetricpixels.questy.quest.Quest;
import com.volumetricpixels.questy.quest.QuestInstance;
import com.volumetricpixels.questy.store.QuestStore;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Manages and tracks all active {@link com.volumetricpixels.questy.quest.Quest}s and {@link QuestLoader}s.
 */
public class QuestManager {
    /**
     * The Questy {@link QuestyEventManager}.
     */
    private final QuestyEventManager eventManager;
    /**
     * All current {@link QuestLoader}s in use.
     */
    private final Set<QuestLoader> loaders;
    /**
     * All currently loaded {@link com.volumetricpixels.questy.quest.Quest}s.
     */
    private final Set<Quest> loaded;
    /**
     * All current {@link com.volumetricpixels.questy.quest.QuestInstance}s.
     */
    private final Set<QuestInstance> current;
    /**
     * All completed {@link QuestInstance}s.
     */
    private final Set<QuestInstance> completed;

    /**
     * The {@link com.volumetricpixels.questy.store.QuestStore} used for progression data storage.
     */
    private QuestStore store;

    /**
     * Constructs a blank {@link QuestManager} with no registered {@link
     * QuestLoader}s or loaded {@link Quest}s.
     */
    public QuestManager() {
        this.eventManager = new QuestyEventManager(this);
        this.loaders = new HashSet<>();
        this.loaded = new HashSet<>();
        this.current = new HashSet<>();
        this.completed = new HashSet<>();
    }

    /**
     * Constructs a blank {@link QuestManager} with no registered {@link
     * QuestLoader}s or loaded {@link Quest}s, with the given {@link QuestStore}
     * being used for saving / loading progression.
     */
    public QuestManager(QuestStore store) {
        this();
        this.store = store;
    }

    public QuestyEventManager getEventManager() {
        return eventManager;
    }

    public QuestStore getStore() {
        return store;
    }

    public Set<Quest> getLoadedQuests() {
        return new HashSet<>(loaded);
    }

    public Set<QuestLoader> getQuestLoaders() {
        return new HashSet<>(loaders);
    }

    public void setStore(QuestStore store) {
        this.store = store;
    }

    /**
     * Gets the {@link Quest} with the given {@code name}. May return {@code
     * null} if there isn't a {@link Quest} with the given {@code name}.
     *
     * @param name the name of the {@link Quest} to get
     * @return the {@link Quest} with the given name, or {@code null} if there
     *         isn't one
     */
    public Quest getQuest(String name) {
        for (Quest quest : getLoadedQuests()) {
            if (quest.getName().equalsIgnoreCase(name)) {
                return quest;
            }
        }
        return null;
    }

    /**
     * Loads all of the {@link Quest}s in the given {@link File} directory which
     * can be loaded by the registered {@link QuestLoader}.
     *
     * @param directory the directory to load {@link Quest}s from
     */
    public void loadQuests(File directory) {
        loaders.forEach((ql) -> loaded.addAll(ql.loadQuests(directory)));
    }

    /**
     * Loads quest progression data from this {@link QuestManager}'s {@link
     * QuestStore}.
     *
     * @throws NullPointerException if the store is null
     */
    public void loadProgression() {
        if (store == null) {
            throw new NullPointerException("store mustn't be null");
        }

        doLoadProgression(store.loadCurrentQuestData(), current);
        doLoadProgression(store.loadCompletedQuestData(), completed);
    }

    /**
     * Stores all currently loaded quest progression to the {@link QuestStore}.
     *
     * @throws NullPointerException if the store is null
     */
    public void storeProgression() {
        if (store == null) {
            throw new NullPointerException("store mustn't be null");
        }

        store.saveCurrentQuestData(serialize(current));
        store.saveCompletedQuestData(serialize(completed));
    }

    private void doLoadProgression(Map<String, Map<String, String>> map,
            Set<QuestInstance> set) {
        for (Entry<String, Map<String, String>> ply : map.entrySet()) {
            String quester = ply.getKey();
            Map<String, String> quests = ply.getValue();
            for (Entry<String, String> quest : quests.entrySet()) {
                Quest qst = getQuest(quest.getKey());
                String serial = quest.getValue();
                set.add(QuestInstance.deserialize(qst, quester, serial));
            }
        }
    }

    private Map<String, Map<String, String>> serialize(Set<QuestInstance> set) {
        Map<String, Map<String, String>> result = new HashMap<>();
        for (QuestInstance questInstance : set) {
            String quester = questInstance.getQuester();
            Map<String, String> map = result.get(quester);
            if (map == null) {
                map = new HashMap<>();
                result.put(quester, map);
            }

            map.put(questInstance.getQuest().getName(),
                    questInstance.serializeProgression());
        }

        return result;
    }

    public void startQuest(QuestInstance instance) {
        current.add(instance);
        eventManager.fire(new QuestStartEvent(instance));
    }

    public void abandonQuest(QuestInstance instance) {
        current.remove(instance);
        eventManager.fire(new QuestAbandonEvent(instance));
    }

    public void completeQuest(QuestInstance instance) {
        current.remove(instance);
        completed.add(instance);
        eventManager.fire(new QuestCompleteEvent(instance));
    }

    /**
     * Adds the given {@link Quest} to this {@link QuestManager}'s loaded
     * {@link Quest} {@link Set}.
     *
     * @param quest the {@link Quest} to add
     * @return whether the operation was successful
     */
    public boolean addQuest(Quest quest) {
        return loaded.add(quest);
    }

    /**
     * Removes the given {@link Quest} from this {@link QuestManager}'s loaded
     * {@link Quest} {@link Set}.
     *
     * @param quest the {@link Quest} to remove
     * @return whether the operation was successful
     */
    public boolean removeQuest(Quest quest) {
        return loaded.remove(quest);
    }

    /**
     * Adds the given {@link QuestLoader} to this {@link QuestManager}'s loaded
     * {@link QuestLoader} {@link Set}.
     *
     * @param loader the {@link QuestLoader} to add
     * @return whether the operation was successful
     */
    public boolean addQuestLoader(QuestLoader loader) {
        return loaders.add(loader);
    }

    /**
     * Removes the given {@link QuestLoader} from this {@link QuestManager}'s
     * loaded {@link QuestLoader} {@link Set}.
     *
     * @param loader the {@link Quest} to remove
     * @return whether the operation was successful
     */
    public boolean removeQuestLoader(QuestLoader loader) {
        return loaders.remove(loader);
    }
}
