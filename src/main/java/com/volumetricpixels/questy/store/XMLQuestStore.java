package com.volumetricpixels.questy.store;

import java.io.File;
import java.util.Map;

/**
 * Stores quest progression data in XML.
 *
 * TODO: implement
 */
public class XMLQuestStore extends QuestStore {
    private final File currentStore;
    private final File completedStore;

    public XMLQuestStore(File storageDirectory) {
        this.currentStore = new File(storageDirectory, "current.xml");
        this.completedStore = new File(storageDirectory, "completed.xml");
    }

    public XMLQuestStore(File currentStore, File completedStore) {
        this.currentStore = currentStore;
        this.completedStore = completedStore;
    }

    @Override
    public void saveCurrentQuestData(Map<String, Map<String, String>> data) {
        doSaveData(currentStore, data);
    }

    @Override
    public Map<String, Map<String, String>> loadCurrentQuestData() {
        return doLoadData(currentStore);
    }

    @Override
    public void saveCompletedQuestData(Map<String, Map<String, String>> data) {
        doSaveData(completedStore, data);
    }

    @Override
    public Map<String, Map<String, String>> loadCompletedQuestData() {
        return doLoadData(completedStore);
    }

    private void doSaveData(File file, Map<String, Map<String, String>> data) {
        // TODO
    }

    private Map<String, Map<String, String>> doLoadData(File file) {
        // TODO
        return null;
    }
}
