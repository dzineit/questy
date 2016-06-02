/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.test;

import org.junit.Assert;
import org.junit.Test;

import com.volumetricpixels.questy.questy.SimpleQuestManager;
import com.volumetricpixels.questy.loading.QuestBuilder;
import com.volumetricpixels.questy.loading.QuestBuilder.ObjectiveBuilder.OutcomeBuilder;
import com.volumetricpixels.questy.Quest;
import com.volumetricpixels.questy.objective.Objective;
import com.volumetricpixels.questy.objective.Outcome;

public class QuestBuilderTest {
    @Test
    public void testQuestBuilding() {
        SimpleQuestManager mgr = new SimpleQuestManager(null);
        QuestBuilder builder = QuestBuilder.begin(mgr, "One");
        // assert that QuestBuilder caching works
        Assert.assertEquals(builder, QuestBuilder.begin(mgr, "One"));

        builder.description("My first quest!");

        QuestBuilder.ObjectiveBuilder objOne = builder.objective("Tree");
        // assert that ObjectiveBuilder caching works
        Assert.assertEquals(objOne, builder.objective("Tree"));

        objOne.description("The first objective!");

        OutcomeBuilder outcome1 = objOne.outcome("Nope");
        OutcomeBuilder outcome2 = objOne.outcome("Yep");

        Quest result = builder.build();
        Assert.assertEquals(result.getAmtObjectives(), 1);

        Assert.assertNotNull(result.getObjective("Tree"));
        Objective obj = result.getObjective("Tree");

        Assert.assertEquals(obj.getOutcomes().length, 2);
        Outcome outcome = obj.getOutcome("Nope");
        Assert.assertNotNull(outcome);
    }
}
