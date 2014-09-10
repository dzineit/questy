/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.event.quest.objective;

import com.volumetricpixels.questy.quest.QuestInstance;
import com.volumetricpixels.questy.event.quest.QuestEvent;

public abstract class QuestObjectiveEvent extends QuestEvent {
    // TODO: add objectives
    protected QuestObjectiveEvent(QuestInstance quest) {
        super(quest);
    }
}
