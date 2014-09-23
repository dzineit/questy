/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.test;

import org.junit.Assert;
import org.junit.Test;

import com.volumetricpixels.questy.Quest;
import com.volumetricpixels.questy.QuestManager;
import com.volumetricpixels.questy.loading.impl.JSQuestLoader;
import com.volumetricpixels.questy.objective.Objective;
import com.volumetricpixels.questy.objective.Outcome;

import java.io.StringReader;

public class JavaScriptQuestLoaderTest {
    @Test
    public void runTest() {
        QuestManager qm = new QuestManager(null);
        JSQuestLoader loader = new JSQuestLoader(qm);

        Quest quest = loader.loadQuest(new StringReader(generateJS()));

        Assert.assertNotNull(quest);
        Assert.assertEquals(quest.getName(), "Test");
        Assert.assertEquals(quest.getDescription(), "Test Description");
        Assert.assertEquals(quest.getAmtObjectives(), 1);

        Objective objective = quest.getObjective("banter");
        Assert.assertNotNull(objective);
        Assert.assertEquals(objective.getDescription(), "Some great banter");

        Outcome outcome = objective.getOutcome("lols");
        Assert.assertNotNull(outcome);
        Assert.assertEquals(outcome.getDescription(), "lel");
        Assert.assertEquals(outcome.getType(), "banter");
    }

    private String generateJS() {
        return "var quest = function genQuest() {" +
                "    var builder = QuestLoadHelper.quest(questManager, 'Test');"
                +
                "    builder.description('Test Description');" +
                "    " +
                "    var obj = builder.objective('banter');" +
                "    obj.description('Some great banter');" +
                "    " +
                "    var outcome = obj.outcome('lols');" +
                "    outcome.description('lel').type('banter');" +
                "    " +
                "    return builder.build();" +
                "}";
    }
}
