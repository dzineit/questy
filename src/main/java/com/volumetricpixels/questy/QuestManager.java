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
import com.volumetricpixels.questy.loading.QuestLoader;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Manages and tracks all active {@link Quest}s and {@link QuestLoader}s.
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
     * All currently loaded {@link Quest}s.
     */
    private final Set<Quest> loaded;

    /**
     * Constructs a blank {@link QuestManager} with no registered {@link
     * QuestLoader}s or loaded {@link Quest}s.
     */
    public QuestManager() {
        this.eventManager = new QuestyEventManager(this);
        this.loaders = new HashSet<>();
        this.loaded = new HashSet<>();
    }

    public Set<Quest> getLoadedQuests() {
        return new HashSet<>(loaded);
    }

    public Set<QuestLoader> getQuestLoaders() {
        return new HashSet<>(loaders);
    }

    public QuestyEventManager getEventManager() {
        return eventManager;
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
}
