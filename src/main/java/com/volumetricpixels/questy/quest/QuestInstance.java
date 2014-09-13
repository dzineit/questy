/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.quest;

import com.volumetricpixels.questy.QuestManager;
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

        objectiveProgresses = new ObjectiveProgress[quest.getAmtObjectives()];
        quest.populateObjectiveProgresses(this, objectiveProgresses);
    }

    private QuestInstance(QuestManager questManager, String quester,
            String serialized) {
        this.quester = quester;

        String[] split = serialized.split("_");
        this.quest = questManager.getQuest(split[0]);
        String[] progressions = split[1].split("|||");
        objectiveProgresses = new ObjectiveProgress[progressions.length];
        for (int i = 0; i < progressions.length; i++) {
            objectiveProgresses[i] = ObjectiveProgress
                    .deserialize(this, progressions[i]);
        }
    }

    public Quest getQuest() {
        return quest;
    }

    public String getQuester() {
        return quester;
    }

    public ObjectiveProgress[] getObjectiveProgresses() {
        return objectiveProgresses.clone();
    }

    public String serializeProgression() {
        StringBuilder result = new StringBuilder(quest.getName()).append("_");
        for (ObjectiveProgress progress : objectiveProgresses) {
            result.append(progress.serialize()).append("|||");
        }
        result.setLength(result.length() - 3);
        return result.toString();
    }

    public static QuestInstance deserialize(QuestManager manager,
            String quester, String progression) {
        return new QuestInstance(manager, quester, progression);
    }
}
