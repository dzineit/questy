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
