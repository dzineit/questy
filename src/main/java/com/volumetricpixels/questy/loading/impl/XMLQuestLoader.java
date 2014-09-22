/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.loading.impl;

import com.volumetricpixels.questy.QuestManager;
import com.volumetricpixels.questy.loading.QuestLoader;
import com.volumetricpixels.questy.Quest;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Loads XML quests.
 */
public class XMLQuestLoader implements QuestLoader {
    private final QuestManager questManager;

    public XMLQuestLoader(QuestManager questManager) {
        this.questManager = questManager;
    }


    @Override
    public Set<Quest> loadQuests(File directory) {
        if (directory == null || !directory.isDirectory()) {
            return null;
        }

        Set<Quest> result = new HashSet<>();
        // iterate through all files in the directory which end with .xml
        for (File file : directory.listFiles(fl -> endsWith(fl, ".xml"))) {
            // TODO
        }

        return result;
    }

    @Override
    public String getQuestFormat() {
        return "XML";
    }
}
