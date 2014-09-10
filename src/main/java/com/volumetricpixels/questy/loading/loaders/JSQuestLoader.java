/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.loading.loaders;

import com.volumetricpixels.questy.loading.QuestLoader;
import com.volumetricpixels.questy.quest.Quest;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class JSQuestLoader implements QuestLoader {
    @Override
    public Set<Quest> loadQuests(File directory) {
        return new HashSet<>();
    }

    @Override
    public String getQuestFormat() {
        return "JavaScript";
    }
}
