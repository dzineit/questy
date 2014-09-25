/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.objective;

import com.volumetricpixels.questy.Quest;
import com.volumetricpixels.questy.QuestInstance;

/**
 * Represents a single objective in a {@link Quest}.
 */
public class Objective {
    private final String name;
    private final String description;
    private final Outcome[] outcomes;

    public Objective(String name, String description, Outcome[] outcomes) {
        this.name = name;
        this.description = description;
        this.outcomes = outcomes;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Outcome[] getOutcomes() {
        return outcomes.clone();
    }

    public int getAmtOutcomes() {
        return outcomes.length;
    }

    public Outcome getOutcome(String outcome) {
        for (Outcome curOutcome : outcomes) {
            if (curOutcome.getName().equals(outcome)) {
                return curOutcome;
            }
        }
        return null;
    }

    void populateOutcomeProgresses(QuestInstance qst, OutcomeProgress[] array) {
        assert array.length == outcomes.length;

        for (int i = 0; i < array.length; i++) {
            array[i] = new OutcomeProgress(qst, outcomes[i]);
        }
    }
}
