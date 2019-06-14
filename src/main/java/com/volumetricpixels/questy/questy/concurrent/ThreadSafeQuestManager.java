/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.questy.concurrent;

import gnu.trove.map.hash.THashMap;
import gnu.trove.set.hash.THashSet;

import com.volumetricpixels.questy.Quest;
import com.volumetricpixels.questy.QuestInstance;
import com.volumetricpixels.questy.QuestManager;
import com.volumetricpixels.questy.event.EventManager;
import com.volumetricpixels.questy.event.quest.QuestAbandonEvent;
import com.volumetricpixels.questy.event.quest.QuestCompleteEvent;
import com.volumetricpixels.questy.event.quest.QuestStartEvent;
import com.volumetricpixels.questy.event.quest.objective.ObjectiveStartEvent;
import com.volumetricpixels.questy.questy.event.SimpleEventManager;
import com.volumetricpixels.questy.loading.QuestLoader;
import com.volumetricpixels.questy.objective.OutcomeProgress;
import com.volumetricpixels.questy.storage.ProgressStore;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Unfinished / untested - currently no guarantee of thread safety
 */
public class ThreadSafeQuestManager implements QuestManager {
    /**
     * The {@link ProgressStore} used for progression data storage.
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
    private final Map<String, Quest> loaded;
    /**
     * All current {@link QuestInstance}s.
     */
    private final Set<QuestInstance> current;
    /**
     * All completed {@link QuestInstance}s.
     */
    private final Set<QuestInstance> completed;
    /**
     * The {@link QuestLoadHelper} helper used for {@link QuestBuilder} caching.
     */
    private final QuestLoadHelper questLoadHelper = new QuestLoadHelper();

    /**
     * Constructs a blank {@link ThreadSafeQuestManager} with no registered
     * {@link QuestLoader}s or loaded {@link Quest}s, with the given {@link
     * ProgressStore} being used for saving / loading progression.
     *
     * A {@link SimpleEventManager} is used for events.
     *
     * @param store the {@link ProgressStore} for saving and loading progression
     *        data - can be null but {@link NullPointerException} will be thrown
     *        from {@link #loadProgression()} / {@link #saveProgression()} if it
     *        is
     */
    public ThreadSafeQuestManager(ProgressStore store) {
        this(store, new SimpleEventManager());
    }

    /**
     * Constructs a blank {@link ThreadSafeQuestManager} with no registered
     * {@link QuestLoader}s or loaded {@link Quest}s, with the given {@link
     * ProgressStore} being used for saving / loading progression.
     *
     * @param store the {@link ProgressStore} for saving and loading progression
     *        data - can be null but {@link NullPointerException} will be thrown
     *        from {@link #loadProgression()} / {@link #saveProgression()} if it
     *        is
     * @param eventManager the {@link EventManager} implementation to use for
     *        events
     */
    public ThreadSafeQuestManager(ProgressStore store, EventManager eventManager) {
        this.store = store;
        this.eventManager = eventManager;
        this.loaders = new THashSet<>();
        this.loaded = new THashMap<>();
        this.current = new THashSet<>();
        this.completed = new THashSet<>();
    }

    @Override
    public ProgressStore getProgressStore() {
        return store;
    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public Map<String, Quest> getLoadedQuests() {
        return new THashMap<>(loaded);
    }

    @Override
    public QuestLoadHelper getQuestLoadHelper() {
        return questLoadHelper;
    }

    @Override
    public Quest getQuest(String name) {
        synchronized (loaded) {
            return loaded.get(name);
        }
    }

    @Override
    public QuestInstance getQuestInstance(Quest quest, String quester) {
        synchronized (current) {
            return current.stream()
                    .filter(inst -> inst.getInfo().equals(quest))
                    .filter(inst -> inst.getQuester().equals(quester))
                    .findFirst().orElse(null);
        }
    }

    @Override
    public QuestInstance getCompletedQuest(Quest quest, String quester) {
        synchronized (completed) {
            return completed.stream()
                    .filter(inst -> inst.getInfo().equals(quest))
                    .filter(inst -> inst.getQuester().equals(quester))
                    .findFirst().orElse(null);
        }
    }

    @Override
    public boolean hasCompleted(Quest quest, String quester) {
        return getCompletedQuest(quest, quester) != null;
    }

    @Override
    public Collection<QuestInstance> getQuestInstances(String quester) {
        synchronized (current) {
            return current.stream()
                    .filter(instance -> instance.getQuester().equals(quester))
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public Collection<QuestInstance> getInstances(Quest quest) {
        synchronized (current) {
            return current.stream()
                    .filter(instance -> instance.getInfo().equals(quest))
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public void loadQuests(File directory) {
        synchronized (loaders) {
            if (loaders.isEmpty()) {
                return;
            }

            synchronized (loaded) {
                loaders.forEach(ql -> loaded.putAll(ql.loadQuests(directory)));
            }
        }
    }

    @Override
    public void loadProgression() {
        if (store == null) {
            throw new NullPointerException("store mustn't be null");
        }

        synchronized (store) {
            deserialize(store.loadCurrentQuestData(), current);
            deserialize(store.loadCompletedQuestData(), completed);
        }
    }

    @Override
    public void saveProgression() {
        if (store == null) {
            throw new NullPointerException("store mustn't be null");
        }

        synchronized (store) {
            store.saveCurrentQuestData(serialize(current));
            store.saveCompletedQuestData(serialize(completed));
        }
    }

    @Override
    public boolean startQuest(QuestInstance instance) {
        synchronized (current) {
            if (current.add(instance)) {
                synchronized (eventManager) {
                    QuestStartEvent event = eventManager.fire(new QuestStartEvent(instance));
                    eventManager.fire(new ObjectiveStartEvent(instance, instance.getCurrentObjective(), event));
                }
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean abandonQuest(QuestInstance instance) {
        synchronized (current) {
            return !eventManager.fire(new QuestAbandonEvent(instance)).isCancelled() && current.remove(instance);
        }
    }

    @Override
    public boolean completeQuest(QuestInstance instance, OutcomeProgress outcome) {
        synchronized (current) {
            synchronized (completed) {
                if (current.remove(instance) && completed.add(instance)) {
                    eventManager.fire(new QuestCompleteEvent(instance, outcome));
                    return true;
                }
                return false;
            }
        }
    }

    @Override
    public boolean addQuest(Quest quest) {
        synchronized (loaded) {
            loaded.put(quest.getName(), quest);
        }
        return true; // this implementation overwrites quests so always true
    }

    @Override
    public boolean removeQuest(Quest quest) {
        synchronized (loaded) {
            return loaded.remove(quest.getName()) != null;
        }
    }

    @Override
    public boolean addLoader(QuestLoader loader) {
        synchronized (loaders) {
            return loaders.add(loader);
        }
    }

    @Override
    public boolean removeLoader(QuestLoader loader) {
        synchronized (loaders) {
            return loaders.remove(loader);
        }
    }

    // internal

    private void deserialize(Map<String, Map<String, String>> map,
            Set<QuestInstance> set) {
        for (String key : map.keySet()) {
            Collection<String> serialized = map.get(key).values();
            set.addAll(serialized.stream().map(serial -> new QuestInstance(this, key, serial)).collect(Collectors.toList()));
        }
    }

    private Map<String, Map<String, String>> serialize(Set<QuestInstance> set) {
        Map<String, Map<String, String>> result = new THashMap<>();
        for (QuestInstance questInstance : set) {
            String quester = questInstance.getQuester();
            Map<String, String> map = result.get(quester);
            if (map == null) {
                map = new THashMap<>();
                result.put(quester, map);
            }

            map.put(questInstance.getInfo().getName(), questInstance.serializeProgression());
        }

        return result;
    }
}
