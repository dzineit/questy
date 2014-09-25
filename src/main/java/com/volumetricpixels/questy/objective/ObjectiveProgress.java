/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.objective;

import com.volumetricpixels.questy.QuestInstance;

public class ObjectiveProgress {
    private final QuestInstance quest;
    private final Objective objective;
    private OutcomeProgress[] outcomeProgresses;

    public ObjectiveProgress(QuestInstance quest, Objective objective) {
        this.quest = quest;
        this.objective = objective;

        outcomeProgresses = new OutcomeProgress[objective.getAmtOutcomes()];
        objective.populateOutcomeProgresses(quest, outcomeProgresses);
    }

    public ObjectiveProgress(QuestInstance quest, String serialized) {
        this.quest = quest;

        String[] split = serialized.split("//");
        Objective obj = quest.getQuest().getObjective(split[0]);
        this.objective = obj;

        String[] progresses = split[1].split("&&");
        outcomeProgresses = new OutcomeProgress[progresses.length];
        for (int idx = 0; idx < progresses.length; idx++) {
            outcomeProgresses[idx] = new OutcomeProgress(quest, obj,
                    progresses[idx]);
        }
    }

    public QuestInstance getQuest() {
        return quest;
    }

    public Objective getObjective() {
        return objective;
    }

    public String serialize() {
        StringBuilder res = new StringBuilder(objective.getName() + "//");
        for (OutcomeProgress progress : outcomeProgresses) {
            res.append(progress.serialize()).append("&&");
        }
        // remove last &&
        res.setLength(res.length() - 2);
        return res.toString();
    }
}
