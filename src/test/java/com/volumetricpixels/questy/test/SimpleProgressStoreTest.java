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
import com.volumetricpixels.questy.QuestManager;
import com.volumetricpixels.questy.storage.impl.SimpleProgressStore;

import java.io.File;

public class SimpleProgressStoreTest {
    @Test
    public void runTest() {
        File rootDir = new File(".");
        QuestManager qm = new QuestManager(new SimpleProgressStore(rootDir));
        File testDir = new File(rootDir, "test");
        boolean existed = rootDir.exists();
        if (!existed) testDir.mkdirs();

        Quest t = TEST.obtainTestQuest(qm);
        qm.addQuest(t);
        qm.startQuest(t.createInstance("bob"));

        qm.storeProgression();
        qm.loadProgression();

        QuestInstance i = qm.getQuestInstance(t, "bob");
        Assert.assertNotNull(i);
        Assert.assertNotNull(i.getCurrentObjective());

        if (!existed) testDir.delete();
    }
}
