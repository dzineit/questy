package com.volumetricpixels.questy.quest;

import com.volumetricpixels.questy.quest.objective.Objective;

/**
 * Represents the 'outline' of a quest. There is a single {@link Quest} object
 * created for each scripted / configured quest which is loaded.
 */
public final class Quest {
    private final String name;
    private final String description;
    private final Objective[] objectives;

    public Quest(String name, String description, Objective[] objectives) {
        this.name = name;
        this.description = description;
        this.objectives = objectives;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Objective[] getObjectives() {
        return objectives;
    }

    public QuestInstance createInstance(String quester) {
        return new QuestInstance(this, quester);
    }
}
