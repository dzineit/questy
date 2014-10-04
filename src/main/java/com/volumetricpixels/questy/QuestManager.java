/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy;

import gnu.trove.map.hash.THashMap;

import com.volumetricpixels.questy.event.EventManager;
import com.volumetricpixels.questy.loading.QuestBuilder;
import com.volumetricpixels.questy.loading.QuestLoader;
import com.volumetricpixels.questy.objective.OutcomeProgress;
import com.volumetricpixels.questy.storage.ProgressStore;

import java.io.File;
import java.util.Map;

/**
 * Manages and tracks all active {@link Quest}s and {@link QuestLoader}s.
 */
public interface QuestManager {
    /**
     * Gets the {@link ProgressStore} implementation used for storage of Quest
     * progression data in this {@link QuestManager}.
     *
     * @return this QuestManager's {@link ProgressStore} object
     */
    ProgressStore getProgressStore();

    /**
     * Gets the Questy {@link EventManager}. In most cases (outside of custom
     * events), the {@link EventManager} doesn't need to be used outside of the
     * Questy code.
     *
     * @return the Questy {@link EventManager}
     */
    EventManager getEventManager();

    /**
     * Gets a {@link Map} of quest names to {@link Quest} objects associated
     * with this {@link QuestManager}.
     *
     * @return a {@link Map} of quest names to this manager's {@link Quest}s
     */
    Map<String, Quest> getLoadedQuests();

    /**
     * Gets the {@link Quest} with the given {@code name}. May return {@code
     * null} if there isn't a {@link Quest} with the given {@code name}.
     *
     * @param name the name of the {@link Quest} to get
     * @return the {@link Quest} with the given name, or {@code null} if there
     *         isn't one
     */
    Quest getQuest(String name);

    /**
     * Adds the given {@link Quest} to this {@link QuestManager}'s loaded
     * {@link Quest}s.
     *
     * @param quest the {@link Quest} to add
     * @return whether the operation was successful
     */
    boolean addQuest(Quest quest);

    /**
     * Removes the given {@link Quest} from this {@link QuestManager}'s loaded
     * {@link Quest}s.
     *
     * @param quest the {@link Quest} to remove
     * @return whether the operation was successful
     */
    boolean removeQuest(Quest quest);

    /**
     * Adds the given {@link QuestLoader} to this {@link QuestManager}'s loaded
     * {@link QuestLoader}s.
     *
     * @param loader the {@link QuestLoader} to add
     * @return whether the operation was successful
     */
    boolean addLoader(QuestLoader loader);

    QuestLoadHelper getQuestLoadHelper();

    /**
     * Removes the given {@link QuestLoader} from this {@link QuestManager}'s
     * loaded {@link QuestLoader}s.
     *
     * @param loader the {@link Quest} to remove
     * @return whether the operation was successful
     */
    boolean removeLoader(QuestLoader loader);

    /**
     * Gets the {@link QuestInstance} object of the given {@link Quest} for the
     * given {@code quester}. This may be {@code null} if the given quester
     * isn't currently doing the given quest.
     *
     * @param quest the {@link Quest} to get the instance for
     * @param quester the name of the player to get the instance for
     * @return the given player's progress for the given Quest
     */
    QuestInstance getQuestInstance(Quest quest, String quester);

    /**
     * Gets progression data about the given {@link Quest} for the given player
     * if the given player has completed the {@link Quest}. If the player hasn't
     * completed the quest, {@code null} is returned.
     *
     * @param quest the {@link Quest} to get the progression for
     * @param quester the player to get the completed quest data for
     * @return the completed quest data for the given quest and the given
     *         player, or {@code null} if they haven't completed it
     */
    QuestInstance getCompletedQuest(Quest quest, String quester);

    /**
     * Checks whether the given {@code quester} has completed the given {@link
     * Quest}.
     *
     * @param quest the {@link Quest} to check completion status of
     * @param quester the player to check the completion status for
     * @return {@code true} if the given player has done the given Quest, else
     *         {@code false}
     */
    default boolean hasCompleted(Quest quest, String quester) {
        return getCompletedQuest(quest, quester) != null;
    }

    /**
     * A utility for caching {@link QuestBuilder}s and other quest loading
     * things.
     *
     * Methods in this class are synchronized in order to prevent overwritten
     * builders or {@link java.util.ConcurrentModificationException}.
     */
    // in a separate class for synchronization purposes and organisation
    public final class QuestLoadHelper {
        /**
         * A {@link Map} of quest names to {@link QuestBuilder}s.
         */
        private final Map<String, QuestBuilder> builders = new THashMap<>();

        public QuestLoadHelper() {
        }

        /**
         * Gets the {@link QuestBuilder} for the {@link Quest} with the given
         * {@code questName}, returning {@code null} if there isn't one.
         *
         * @param questName the name of the Quest to get the builder for
         * @return the {@link QuestBuilder} for the given quest
         */
        public synchronized QuestBuilder getBuilder(String questName) {
            return builders.get(questName);
        }

        /**
         * Adds the given {@link QuestBuilder} to this {@link QuestLoadHelper}
         * object's cache.
         *
         * @param questName the name of the quest the builder is for
         * @param builder the {@link QuestBuilder} to cache
         * @return whether the operation was successful - {@code false} if
         *         there was already a builder for the given name
         */
        public synchronized boolean addBuilder(String questName,
                QuestBuilder builder) {
            QuestBuilder existent = builders.put(questName, builder);
            if (existent != null) {
                // no overrides
                builders.put(questName, existent);
                return false;
            }
            return true;
        }
    }

    /**
     * Loads all of the {@link Quest}s in the given {@link File} directory which
     * can be loaded by the registered {@link QuestLoader}.
     *
     * @param directory the directory to load {@link Quest}s from
     */
    void loadQuests(File directory);

    /**
     * Loads quest progression data from this {@link QuestManager}'s {@link
     * ProgressStore}.
     */
    void loadProgression();

    /**
     * Stores all currently loaded quest progression to the {@link
     * ProgressStore}.
     */
    void saveProgression();

    boolean startQuest(QuestInstance quest);

    boolean abandonQuest(QuestInstance quest);

    boolean completeQuest(QuestInstance quest, OutcomeProgress outcome);
}
