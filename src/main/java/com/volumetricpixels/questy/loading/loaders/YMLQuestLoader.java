/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.loading.loaders;

import org.yaml.snakeyaml.Yaml;

import com.volumetricpixels.questy.QuestManager;
import com.volumetricpixels.questy.loading.QuestLoader;
import com.volumetricpixels.questy.loading.QuestLoading;
import com.volumetricpixels.questy.loading.QuestLoading.ObjectiveBuilder;
import com.volumetricpixels.questy.loading.QuestLoading.OutcomeBuilder;
import com.volumetricpixels.questy.loading.QuestLoading.QuestBuilder;
import com.volumetricpixels.questy.quest.Quest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Loads YML quests.
 */
public class YMLQuestLoader implements QuestLoader {
    private final QuestManager questManager;

    public YMLQuestLoader(QuestManager questManager) {
        this.questManager = questManager;
    }

    @Override
    public Set<Quest> loadQuests(File directory) {
        if (directory == null || !directory.isDirectory()) {
            return null;
        }

        Yaml yaml = new Yaml();
        Set<Quest> result = new HashSet<>();
        // iterate through all files in the directory which end with .yml
        for (File file : directory.listFiles(fl -> endsWith(fl, ".yml"))) {
            try {
                result.add(doLoadQuest(yaml, new FileInputStream(file)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public Quest doLoadQuest(Yaml yaml, InputStream stream) {
        Map<?, ?> map = convert((Map<?, ?>) yaml.load(stream));

        QuestBuilder builder = QuestLoading
                .quest(questManager, map.get("name").toString());
        builder.description(map.get("description").toString());

        Map<?, ?> objectives = (Map) map.get("objectives");
        for (Entry<?, ?> objEntry : objectives.entrySet()) {
            ObjectiveBuilder obj = builder
                    .objective(objEntry.getKey().toString());
            Map<?, ?> objMap = (Map) objEntry.getValue();
            obj.description(objMap.get("description").toString());

            Map<?, ?> outcomes = (Map) map.get("outcomes");
            for (Entry<?, ?> ocEntry : outcomes.entrySet()) {
                OutcomeBuilder oc = obj
                        .outcome(ocEntry.getKey().toString());
                Map<?, ?> ocMap = (Map) ocEntry.getValue();
                oc.description(ocMap.get("description").toString());
                oc.type(ocMap.get("type").toString());

                Object next = ocMap.get("next");
                if (next != null) {
                    oc.next(builder.objective(next.toString()));
                }
            }
        }

        return builder.build();
    }

    private Map convert(Map<?, ?> map) {
        Map<Object, Object> result = new HashMap<>();
        for (Entry<?, ?> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (value instanceof Map) {
                result.put(key, convert((Map<?, ?>) value));
            } else {
                result.put(key, value);
            }
        }
        return result;
    }

    @Override
    public String getQuestFormat() {
        return "YAML";
    }
}
