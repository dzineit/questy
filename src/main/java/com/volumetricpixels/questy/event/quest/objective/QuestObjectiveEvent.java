/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.event.quest.objective;

import com.volumetricpixels.questy.event.quest.QuestEvent;
import com.volumetricpixels.questy.quest.QuestInstance;
import com.volumetricpixels.questy.quest.objective.ObjectiveProgress;

public abstract class QuestObjectiveEvent extends QuestEvent {
    private final ObjectiveProgress objective;

    protected QuestObjectiveEvent(QuestInstance quest,
            ObjectiveProgress objective) {
        super(quest);
        this.objective = objective;
    }

    public ObjectiveProgress getObjective() {
        return objective;
    }
}
