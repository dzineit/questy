/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.test;

import com.volumetricpixels.questy.Quest;
import com.volumetricpixels.questy.QuestManager;
import com.volumetricpixels.questy.loading.QuestBuilder;
import com.volumetricpixels.questy.loading.QuestBuilder.ObjectiveBuilder;
import com.volumetricpixels.questy.loading.QuestLoader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TEST {
    public static final Random r = new Random();

    public static Quest obtainTestQuest(QuestManager m) {
        QuestBuilder b = QuestBuilder.begin(m, "test" + r.nextInt());
        ObjectiveBuilder ob = b.description("Test").objective("test1");
        ob.description("test2").outcome("lel").description("lel").type("lel");
        return b.build();
    }

    public static QuestLoader obtainTestLoader(QuestManager m) {
        return new TestQuestLoader(m);
    }

    private static class TestQuestLoader implements QuestLoader {
        private final QuestManager qm;

        public TestQuestLoader(QuestManager qm) {
            this.qm = qm;
        }

        @Override
        public Map<String, Quest> loadQuests(File directory) {
            Quest quest = obtainTestQuest(qm);
            Map<String, Quest> map = new HashMap<>();
            map.put(quest.getName(), quest);
            return map;
        }
    }
}
