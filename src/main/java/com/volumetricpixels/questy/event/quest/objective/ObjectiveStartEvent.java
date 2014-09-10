/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.event.quest.objective;

import com.volumetricpixels.questy.quest.QuestInstance;
import com.volumetricpixels.questy.event.quest.QuestEvent;

public class ObjectiveStartEvent extends QuestObjectiveEvent {
    // should be an instance of either ObjectiveCompleteEvent or QuestStartEvent
    // in most cases
    private final QuestEvent cause;

    public ObjectiveStartEvent(QuestInstance quest, QuestEvent cause) {
        super(quest);
        this.cause = cause;
    }

    public QuestEvent getCause() {
        return cause;
    }
}
