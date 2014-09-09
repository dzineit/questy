/*
 * This file is part of Questy, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 VolumetricPixels <http://volumetricpixels.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.volumetricpixels.questy.test;

import org.junit.Assert;
import org.junit.Test;

import com.volumetricpixels.questy.quest.Quest;
import com.volumetricpixels.questy.QuestManager;
import com.volumetricpixels.questy.event.QuestyEventManager;
import com.volumetricpixels.questy.event.listener.GenericQuestListener;
import com.volumetricpixels.questy.event.listener.QuestListener;
import com.volumetricpixels.questy.event.quest.QuestAbandonEvent;
import com.volumetricpixels.questy.event.quest.QuestEvent;
import com.volumetricpixels.questy.event.quest.QuestStartEvent;
import com.volumetricpixels.questy.loading.QuestLoader;
import com.volumetricpixels.questy.loading.QuestLoaderComparator;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * An all-encompassing (almost, maybe) Questy test.
 */
public class QuestyTesty {
    private boolean testBoolean = false;

    @Test
    public void runTest() {
        QuestLoader jsLoader = new QuestLoader() {
            @Override
            public Set<Quest> loadQuests(File directory) {
                return new HashSet<>();
            }

            @Override
            public String getQuestFormat() {
                return "JavaScript";
            }
        };

        QuestLoader ymlLoader = new QuestLoader() {
            @Override
            public Set<Quest> loadQuests(File directory) {
                return new HashSet<>();
            }

            @Override
            public String getQuestFormat() {
                return "YML";
            }
        };

        Assert.assertTrue(new QuestLoaderComparator()
                .compare(jsLoader, ymlLoader) != 0);

        QuestManager qm = new QuestManager();
        QuestyEventManager em = qm.getEventManager();

        QuestListener listener = new GenericQuestListener() {
            @Override
            public void onQuestEvent(QuestEvent event) {
                testBoolean = true;
            }
        };

        em.subscribe(listener);
        em.fire(new QuestStartEvent(null));
        Assert.assertTrue(testBoolean);
        em.unsubscribe(listener);
        testBoolean = false;
        em.fire(new QuestAbandonEvent(null));
        Assert.assertFalse(testBoolean);
    }
}
