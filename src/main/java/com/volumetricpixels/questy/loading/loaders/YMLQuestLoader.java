package com.volumetricpixels.questy.loading.loaders;

import com.volumetricpixels.questy.loading.QuestLoader;
import com.volumetricpixels.questy.quest.Quest;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class YMLQuestLoader implements QuestLoader {
    @Override
    public Set<Quest> loadQuests(File directory) {
        return new HashSet<>();
    }

    @Override
    public String getQuestFormat() {
        return "YAML";
    }
}
