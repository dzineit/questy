/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.test;

import org.junit.Assert;
import org.junit.Test;

import com.volumetricpixels.questy.SimpleQuestManager;
import com.volumetricpixels.questy.event.EventManager;
import com.volumetricpixels.questy.event.Listen;
import com.volumetricpixels.questy.event.quest.QuestAbandonEvent;
import com.volumetricpixels.questy.event.quest.QuestStartEvent;

public class EventsTest {
    @Test
    public void testEvents() {
        EventManager evtMgr = new SimpleQuestManager(null).getEventManager();

        TestListener test = new TestListener();
        Assert.assertTrue(evtMgr.register(test));
        Assert.assertFalse(testBool);
        evtMgr.fire(new QuestStartEvent(null));
        Assert.assertTrue(testBool);

        testBool = false;
        evtMgr.unregister(test);
        evtMgr.fire(new QuestStartEvent(null));
        Assert.assertFalse(testBool);

        evtMgr.fire(new QuestAbandonEvent(null));
        Assert.assertFalse(testBool);
    }

    private boolean testBool = false;

    public class TestListener {
        @Listen
        public void onQuestStart(QuestStartEvent event) {
            testBool = true;
        }
    }
}
