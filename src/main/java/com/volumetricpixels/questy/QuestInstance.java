/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy;

import com.volumetricpixels.questy.event.quest.objective.ObjectiveCompleteEvent;
import com.volumetricpixels.questy.event.quest.objective.ObjectiveStartEvent;
import com.volumetricpixels.questy.objective.Objective;
import com.volumetricpixels.questy.objective.ObjectiveProgress;
import com.volumetricpixels.questy.objective.OutcomeProgress;

/**
 * Represents an 'instance' of a {@link Quest}. A {@link QuestInstance} holds
 * the {@link Quest} object it is an instance of, but also holds the player
 * doing the quest and data about said player's progression through the quest.
 */
public final class QuestInstance {
    private final Quest quest;
    private final String quester;
    private final ObjectiveProgress[] objectiveProgresses;

    private int current;

    QuestInstance(Quest quest, String quester) {
        this.quest = quest;
        this.quester = quester;

        objectiveProgresses = new ObjectiveProgress[quest.getAmtObjectives()];
        quest.populateObjectiveProgresses(this, objectiveProgresses);
        current = 0;
    }

    private QuestInstance(QuestManager questManager, String quester,
            String serialized) {
        this.quester = quester;

        String[] split = serialized.split("_");
        this.quest = questManager.getQuest(split[0]);
        String[] progressions = split[1].split("|||");
        objectiveProgresses = new ObjectiveProgress[progressions.length];
        for (int i = 0; i < progressions.length; i++) {
            String serial = progressions[i];
            objectiveProgresses[i] = ObjectiveProgress
                    .deserialize(this, serial);
            if (serial.endsWith("<c>")) {
                current = i;
            }
        }
    }

    public void objectiveComplete(ObjectiveProgress objective,
            OutcomeProgress outcome) {
        Objective next = outcome.getOutcome().getNext();
        if (next == null) {
            quest.getQuestManager().completeQuest(this, outcome);
            return;
        }

        ObjectiveProgress temp = getProgress(next);
        if (temp == null) {
            return;
        }

        ObjectiveCompleteEvent e = quest.getQuestManager().getEventManager()
                .fire(new ObjectiveCompleteEvent(this, objective, outcome));
        current = getIndex(temp);
        quest.getQuestManager().getEventManager()
                .fire(new ObjectiveStartEvent(this, temp, e));
    }

    public void objectiveFailed(ObjectiveProgress objective, boolean endQuest) {
        if (endQuest) {
            quest.getQuestManager().abandonQuest(this);
            return;
        }

        for (int i = 0; i < objectiveProgresses.length; i++) {
            if (objectiveProgresses[i] == objective) {
                objectiveProgresses[i] = new ObjectiveProgress(this,
                        objective.getObjective());
            }
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

    public ObjectiveProgress getProgress(Objective objective) {
        for (ObjectiveProgress cur : objectiveProgresses) {
            if (cur.getObjective() == objective) {
                return cur;
            }
        }
        return null;
    }

    public ObjectiveProgress getCurrentObjective() {
        return objectiveProgresses[current];
    }

    public String serializeProgression() {
        StringBuilder result = new StringBuilder(quest.getName()).append("_");
        for (ObjectiveProgress progress : objectiveProgresses) {
            appendIf(getIndex(progress) == current, result.append(
                    progress.serialize()), "<c>").append("|||");
        }
        result.setLength(result.length() - 3);
        return result.toString();
    }

    public static QuestInstance deserialize(QuestManager manager,
            String quester, String progression) {
        return new QuestInstance(manager, quester, progression);
    }

    private int getIndex(ObjectiveProgress progress) {
        for (int i = 0; i < objectiveProgresses.length; i++) {
            if (objectiveProgresses[i] == progress) {
                return i;
            }
        }
        return -1;
    }

    private StringBuilder appendIf(boolean check, StringBuilder builder,
            String append) {
        if (check) {
            return builder.append(append);
        }
        return builder;
    }
}
