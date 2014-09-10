/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.quest.objective;

/**
 * Represents a single possible outcome for an {@link Objective}.
 */
public class Outcome {
    // TODO: what the outcome leads to (quest end, different objective, etc)
    // TODO: rewards for the outcome / w/e
    // TODO: the requirement for the outcome - i.e kill two cows
    private final String name;
    private final String description;

    public Outcome(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
