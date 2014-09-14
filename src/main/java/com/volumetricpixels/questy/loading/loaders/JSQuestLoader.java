/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.loading.loaders;

import com.volumetricpixels.questy.loading.QuestLoader;
import com.volumetricpixels.questy.quest.Quest;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Loads JavaScript quests.
 */
public class JSQuestLoader implements QuestLoader {
    @Override
    public Set<Quest> loadQuests(File directory) {
        if (directory == null || !directory.isDirectory()) {
            return null;
        }

        Set<Quest> result = new HashSet<>();
        // iterate through all files in the directory which end with .js
        for (File file : directory.listFiles(fl -> endsWith(fl, ".js"))) {
            // TODO
        }

        return result;
    }

    @Override
    public String getQuestFormat() {
        return "JavaScript";
    }
}
