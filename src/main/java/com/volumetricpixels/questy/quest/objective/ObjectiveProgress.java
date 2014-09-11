/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.quest.objective;

import com.volumetricpixels.questy.quest.QuestInstance;

public class ObjectiveProgress {
    private final QuestInstance quest;
    private final Objective objective;
    private OutcomeProgress[] outcomeProgresses;

    public ObjectiveProgress(QuestInstance quest, Objective objective) {
        this.quest = quest;
        this.objective = objective;
    }

    public QuestInstance getQuest() {
        return quest;
    }

    public Objective getObjective() {
        return objective;
    }
}
