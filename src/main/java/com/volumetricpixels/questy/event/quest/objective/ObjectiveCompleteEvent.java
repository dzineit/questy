/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.event.quest.objective;

import com.volumetricpixels.questy.quest.QuestInstance;

public class ObjectiveCompleteEvent extends QuestObjectiveEvent {
    // TODO: add the completion method - for example some objectives could have
    // multiple different ways of completing them
    public ObjectiveCompleteEvent(QuestInstance quest) {
        super(quest);
    }
}
