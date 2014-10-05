/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.test;

import com.volumetricpixels.questy.Quest;
import com.volumetricpixels.questy.SimpleQuestManager;
import com.volumetricpixels.questy.loading.QuestBuilder;
import com.volumetricpixels.questy.loading.QuestBuilder.ObjectiveBuilder;

import java.util.Random;

public class TEST {
    public static final Random r = new Random();

    public static Quest obtainTestQuest(SimpleQuestManager m) {
        QuestBuilder b = QuestBuilder.begin(m, "test" + r.nextInt());
        ObjectiveBuilder ob = b.description("Test").objective("test1");
        ob.description("test2").outcome("lel").description("lel").type("lel");
        return b.build();
    }
}
