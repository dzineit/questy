/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.quest.objective;

import com.volumetricpixels.questy.DeserializeUtils;
import com.volumetricpixels.questy.quest.QuestInstance;

public class OutcomeProgress {
    private final QuestInstance quest;
    private final Outcome outcome;

    private Object progress;

    public OutcomeProgress(QuestInstance quest, Outcome outcome) {
        this.quest = quest;
        this.outcome = outcome;
    }

    private OutcomeProgress(QuestInstance quest, Objective objective,
            String serialized) {
        this.quest = quest;
        String[] split = serialized.split("_");
        this.outcome = objective.getOutcome(split[0]);
        setProgress(DeserializeUtils.handleCommonTypes(split[1]));
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
        return outcome.getName() + "_" + progress.toString();
    }

    public static OutcomeProgress deserialize(QuestInstance instance,
            Objective objective, String serialized) {
        return new OutcomeProgress(instance, objective, serialized);
    }
}
