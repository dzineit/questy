/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.event.quest.progress;

import com.volumetricpixels.questy.event.quest.QuestEvent;
import com.volumetricpixels.questy.quest.QuestInstance;
import com.volumetricpixels.questy.quest.objective.Outcome;
import com.volumetricpixels.questy.quest.objective.OutcomeProgress;

/**
 * Called whenever the progress for a {@link OutcomeProgress} is updated to a
 * new value. This allows tracking completion / progress of a {@link Outcome}.
 * Note that changes to the outcome should not be made while listening to this
 * event.
 */
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

    public String getOutcomeType() {
        return getOutcomeInfo().getType();
    }

    public Outcome getOutcomeInfo() {
        return getOutcome().getOutcome();
    }

    public Object getNewProgress() {
        return newProgress;
    }
}
