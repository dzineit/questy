/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.event.quest.objective;

import com.volumetricpixels.questy.quest.QuestInstance;
import com.volumetricpixels.questy.quest.objective.ObjectiveProgress;
import com.volumetricpixels.questy.quest.objective.OutcomeProgress;

public class ObjectiveCompleteEvent extends QuestObjectiveEvent {
    private final OutcomeProgress outcome;

    public ObjectiveCompleteEvent(QuestInstance quest,
            ObjectiveProgress objective, OutcomeProgress outcome) {
        super(quest, objective);
        this.outcome = outcome;
    }

    public OutcomeProgress getOutcome() {
        return outcome;
    }
}
