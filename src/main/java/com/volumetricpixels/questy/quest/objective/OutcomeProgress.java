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
    // TODO: add progress

    public OutcomeProgress(QuestInstance quest, Outcome outcome) {
        this.quest = quest;
        this.outcome = outcome;
    }

    public Outcome getOutcome() {
        return outcome;
    }
}
