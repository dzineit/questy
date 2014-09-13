/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.quest;

import com.volumetricpixels.questy.quest.objective.ObjectiveProgress;

/**
 * Represents an 'instance' of a {@link Quest}. A {@link QuestInstance} holds
 * the {@link Quest} object it is an instance of, but also holds the player
 * doing the quest and data about said player's progression through the quest.
 */
public final class QuestInstance {
    private final Quest quest;
    private final String quester;

    private ObjectiveProgress[] objectiveProgresses;

    public QuestInstance(Quest quest, String quester) {
        this.quest = quest;
        this.quester = quester;
    }

    private QuestInstance(Quest quest, String quester, String serialized) {
        this(quest, quester);
        // TODO: deserialize
    }

    public Quest getQuest() {
        return quest;
    }

    public String getQuester() {
        return quester;
    }

    // TODO: progression data based methods

    public String serializeProgression() {
        // TODO
        return "";
    }

    public static QuestInstance deserialize(Quest quest, String quester,
            String progression) {
        return new QuestInstance(quest, quester, progression);
    }
}
