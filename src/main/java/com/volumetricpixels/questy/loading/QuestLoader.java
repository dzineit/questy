package com.volumetricpixels.questy.loading;

import com.volumetricpixels.questy.quest.Quest;

import java.io.File;
import java.util.Set;

/**
 * Loads scripted / configured quests. Quests can be defined in any format, but
 * there must be a registered {@link QuestLoader} implementation for the format
 * for it to be loaded.
 */
public interface QuestLoader {
    /**
     * Loads all of the {@link Quest}s that this {@link QuestLoader} can handle
     * which are located in the given {@link File}, which should be a directory,
     * and should exist. Note that this method should <em>NOT</em> throw an
     * exception if there is a file in the given directory of a format which
     * this {@link QuestLoader} cannot load.
     *
     * @param directory the directory to load {@link Quest}s from
     * @return all {@link Quest}s loaded from the given directory
     */
    Set<Quest> loadQuests(File directory);

    /**
     * Gets the name of the format this {@link QuestLoader} loads {@link Quest}s
     * in. For example, in a JavaScript {@link QuestLoader} this method would
     * return "JavaScript".
     *
     * @return the name of the format this loader loads {@link Quest}s of
     */
    String getQuestFormat();
}
