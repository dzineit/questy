package com.volumetricpixels.questy.quest.objective;

import com.volumetricpixels.questy.quest.Quest;

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
        return outcomes;
    }
}
