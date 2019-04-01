/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.test;

import org.junit.Assert;
import org.junit.Test;

import com.volumetricpixels.questy.Quest;
import com.volumetricpixels.questy.QuestInstance;
import com.volumetricpixels.questy.questy.SimpleQuestManager;
import com.volumetricpixels.questy.questy.store.TestProgressStore;

import java.io.File;

public class TestProgressStoreTest {
    @Test
    public void runTest() {
        File rootDir = new File(".").getAbsoluteFile();
        File testDir = new File(rootDir, "test").getAbsoluteFile();
        testDir.delete();
        testDir.mkdirs();
        SimpleQuestManager qm = new SimpleQuestManager(new TestProgressStore(testDir));

        Quest t = TEST.obtainTestQuest(qm);
        qm.addQuest(t);
        qm.startQuest(t.start("bob"));

        qm.saveProgression();

        qm = new SimpleQuestManager(new TestProgressStore(testDir));
        qm.addQuest(t);
        qm.loadQuests(testDir);
        qm.loadProgression();

        QuestInstance i = qm.getQuestInstance(t, "bob");
        Assert.assertNotNull(i);
        Assert.assertNotNull(i.getCurrentObjective());
    }
}
