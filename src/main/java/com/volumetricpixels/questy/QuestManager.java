/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy;

import com.volumetricpixels.questy.event.EventManager;
import com.volumetricpixels.questy.event.quest.QuestAbandonEvent;
import com.volumetricpixels.questy.event.quest.QuestCompleteEvent;
import com.volumetricpixels.questy.event.quest.QuestStartEvent;
import com.volumetricpixels.questy.event.quest.objective.ObjectiveStartEvent;
import com.volumetricpixels.questy.loading.QuestLoader;
import com.volumetricpixels.questy.loading.impl.JSQuestLoader;
import com.volumetricpixels.questy.loading.impl.XMLQuestLoader;
import com.volumetricpixels.questy.loading.impl.YMLQuestLoader;
import com.volumetricpixels.questy.objective.OutcomeProgress;
import com.volumetricpixels.questy.store.ProgressStore;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Manages and tracks all active {@link Quest}s and {@link QuestLoader}s.
 */
public class QuestManager {
    /**
     * The {@link com.volumetricpixels.questy.store.ProgressStore} used for progression data storage.
     */
    private final ProgressStore store;
    /**
     * Questy's {@link EventManager}.
     */
    private final EventManager eventManager;
    /**
     * All current {@link QuestLoader}s in use.
     */
    private final Set<QuestLoader> loaders;
    /**
     * All currently loaded {@link Quest}s.
     */
    private final Set<Quest> loaded;
    /**
     * All current {@link QuestInstance}s.
     */
    private final Set<QuestInstance> current;
    /**
     * All completed {@link QuestInstance}s.
     */
    private final Set<QuestInstance> completed;

    /**
     * Constructs a blank {@link QuestManager} with no registered {@link
     * QuestLoader}s or loaded {@link Quest}s, with the given {@link com.volumetricpixels.questy.store.ProgressStore}
     * being used for saving / loading progression.
     */
    public QuestManager(ProgressStore store) {
        this.store = store;
        this.eventManager = new EventManager();
        this.loaders = new HashSet<>();
        this.loaded = new HashSet<>();
        this.current = new HashSet<>();
        this.completed = new HashSet<>();
    }

    /**
     * Gets the Questy {@link EventManager}. In most cases (outside of custom
     * events), the {@link EventManager} doesn't need to be used outside of the
     * Questy code.
     *
     * @return the Questy {@link EventManager}
     */
    public EventManager getEventManager() {
        return eventManager;
    }

    public Set<Quest> getLoadedQuests() {
        return new HashSet<>(loaded);
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
        for (Quest quest : loaded) {
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
     * com.volumetricpixels.questy.store.ProgressStore}.
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
     * Stores all currently loaded quest progression to the {@link com.volumetricpixels.questy.store.ProgressStore}.
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

    public boolean startQuest(QuestInstance instance) {
        boolean val = current.add(instance);
        if (val) {
            QuestStartEvent event = eventManager
                    .fire(new QuestStartEvent(instance));
            eventManager.fire(new ObjectiveStartEvent(instance,
                    instance.getCurrentObjective(), event));
        }
        return val;
    }

    public boolean abandonQuest(QuestInstance instance) {
        return !eventManager.fire(new QuestAbandonEvent(instance)).isCancelled()
                && current.remove(instance);
    }

    public boolean completeQuest(QuestInstance instance,
            OutcomeProgress outcome) {
        boolean val = current.remove(instance) && completed.add(instance);
        if (val) {
            eventManager.fire(new QuestCompleteEvent(instance, outcome));
        }
        return val;
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
     * Adds all default {@link QuestLoader} implementations.
     *
     * @see {@link #addQuestLoader(QuestLoader)}
     */
    public void addDefaultLoaders() {
        addQuestLoader(new JSQuestLoader(this));
        addQuestLoader(new YMLQuestLoader(this));
        addQuestLoader(new XMLQuestLoader(this));
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

    // internal

    private void doLoadProgression(Map<String, Map<String, String>> map,
            Set<QuestInstance> set) {
        for (Entry<String, Map<String, String>> ply : map.entrySet()) {
            Collection<String> serials = ply.getValue().values();
            set.addAll(serials.stream().map(serial -> QuestInstance.deserialize(
                    this, ply.getKey(), serial)).collect(Collectors.toList()));
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
}
