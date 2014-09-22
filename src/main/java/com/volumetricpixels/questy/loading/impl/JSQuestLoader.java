/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.loading.impl;

import com.volumetricpixels.questy.Quest;
import com.volumetricpixels.questy.QuestManager;
import com.volumetricpixels.questy.loading.QuestLoader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

/**
 * Loads JavaScript quests.
 */
public class JSQuestLoader implements QuestLoader {
    private final QuestManager questManager;

    public JSQuestLoader(QuestManager questManager) {
        this.questManager = questManager;
    }

    @Override
    public Set<Quest> loadQuests(File directory) {
        if (directory == null || !directory.isDirectory()) {
            return null;
        }

        Set<Quest> result = new HashSet<>();
        // iterate through all files in the directory which end with .js
        for (File file : directory.listFiles(fl -> endsWith(fl, ".js"))) {
            try {
                Quest q = loadQuest(new FileReader(file));
                if (q != null) {
                    result.add(q);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public Quest loadQuest(Reader reader) {
        ScriptEngine e = new ScriptEngineManager().getEngineByName("nashorn");
        e.put("questManager", questManager);
        try {
            e.eval(reader);
            return (Quest) ((Invocable) e).invokeFunction("quest");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String getQuestFormat() {
        return "JavaScript";
    }
}
