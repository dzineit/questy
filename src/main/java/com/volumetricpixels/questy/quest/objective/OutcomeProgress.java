/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.quest.objective;

import com.volumetricpixels.questy.quest.QuestInstance;

public class OutcomeProgress {
    private final QuestInstance quest;
    private final Outcome outcome;

    private Object progress;

    public OutcomeProgress(QuestInstance quest, Outcome outcome) {
        this.quest = quest;
        this.outcome = outcome;
    }

    private OutcomeProgress(QuestInstance quest, Outcome outcome,
            String serialized) {
        this(quest, outcome);
        // TODO: deserialize
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public Object getProgress() {
        return progress;
    }

    public void setProgress(Object progress) {
        this.progress = progress;
    }

    public String serialize() {
        // TODO
        return "";
    }

    public static OutcomeProgress deserialize(QuestInstance instance,
            Outcome outcome, String serialized) {
        return new OutcomeProgress(instance, outcome, serialized);
    }
}
