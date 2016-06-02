/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.test;

import org.junit.Test;

import com.volumetricpixels.questy.Quest;
import com.volumetricpixels.questy.questy.concurrent.ThreadSafeQuestManager;
import com.volumetricpixels.questy.storage.store.SimpleProgressStore;

import java.io.File;

public class ConcurrentTest {
    @Test
    public void runTest() {
        File rootDir = new File(".").getAbsoluteFile();
        File testDir = new File(rootDir, "test").getAbsoluteFile();
        questManager = new ThreadSafeQuestManager(
                new SimpleProgressStore(testDir));

        Thread threadOne = new ThreadOne();
        Thread threadTwo = new ThreadTwo();
        Thread threadThree = new ThreadThree();
        Thread threadFour = new ThreadFour();

        threadOne.start();
        threadTwo.start();
        threadThree.start();
        threadFour.start();
    }

    private ThreadSafeQuestManager questManager;

    public class ThreadOne extends Thread {
        @Override
        public void run() {
            questManager.saveProgression();
            Quest test = TEST.obtainTestQuest(questManager);
            questManager.addQuest(test);
            questManager.getQuest(test.getName());
        }
    }

    public class ThreadTwo extends Thread {
        @Override
        public void run() {
            questManager.loadProgression();
            Quest test = TEST.obtainTestQuest(questManager);
            questManager.addQuest(test);
            questManager.getQuest(test.getName());
        }
    }

    public class ThreadThree extends Thread {
        @Override
        public void run() {
            questManager.addLoader(TEST.obtainTestLoader(questManager));
        }
    }

    public class ThreadFour extends Thread {
        @Override
        public void run() {
            questManager.addLoader(TEST.obtainTestLoader(questManager));
        }
    }
}
