/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.event.quest.progress;

import com.volumetricpixels.questy.event.quest.QuestEvent;
import com.volumetricpixels.questy.quest.QuestInstance;
import com.volumetricpixels.questy.quest.objective.OutcomeProgress;

public class ProgressUpdateEvent extends QuestEvent {
    private final OutcomeProgress outcome;
    private final Object newProgress;

    public ProgressUpdateEvent(QuestInstance quest, OutcomeProgress outcome) {
        super(quest);
        this.outcome = outcome;
        this.newProgress = outcome.getProgress();
    }

    public OutcomeProgress getOutcome() {
        return outcome;
    }

    public Object getNewProgress() {
        return newProgress;
    }
}
