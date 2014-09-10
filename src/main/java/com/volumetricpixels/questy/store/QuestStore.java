package com.volumetricpixels.questy.store;

import java.util.Map;

/**
 * Stores data about the progression of players into quests and quest chains.
 */
public abstract class QuestStore {
    /**
     * Stores the given quest progression {@code data} for the given {@code
     * player}. The {@code data} parameter is a {@link Map} of quest names to
     * serialized progression data for the quest.
     *
     * @param data the data to store
     */
    public abstract void saveCurrentQuestData(
            Map<String, Map<String, String>> data);

    /**
     * Loads all stored quest progression data from the data store.
     *
     * @return a {@link Map} of player names to stored quest progression data
     */
    public abstract Map<String, Map<String, String>> loadCurrentQuestData();

    /**
     * Stores the given quest progression {@code data} for the given {@code
     * player}. The {@code data} parameter is a {@link Map} of quest names to
     * serialized progression data for the quest.
     *
     * @param data the data to store
     */
    public abstract void saveCompletedQuestData(
            Map<String, Map<String, String>> data);

    /**
     * Loads all stored quest progression data from the data store.
     *
     * @return a {@link Map} of player names to stored quest progression data
     */
    public abstract Map<String, Map<String, String>> loadCompletedQuestData();
}
