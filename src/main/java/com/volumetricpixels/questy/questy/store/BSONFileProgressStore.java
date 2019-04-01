/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.questy.store;

import com.volumetricpixels.questy.storage.ProgressStore;
import gnu.trove.map.hash.THashMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class BSONFileProgressStore implements ProgressStore {
    private final File currentStore;
    private final File completedStore;

    public BSONFileProgressStore(File storageDirectory) {
        this(new File(storageDirectory, "current.bson"),
                new File(storageDirectory, "completed.bson"));
    }

    private BSONFileProgressStore(File currentStore, File completedStore) {
        this.currentStore = currentStore;
        this.completedStore = completedStore;
    }

    @Override
    public void saveCurrentQuestData(Map<String, Map<String, String>> data) {
        doSaveData(currentStore, data, "current.bson");
    }

    @Override
    public Map<String, Map<String, String>> loadCurrentQuestData() {
        return doLoadData(currentStore);
    }

    @Override
    public void saveCompletedQuestData(Map<String, Map<String, String>> data) {
        doSaveData(completedStore, data, "completed.bson");
    }

    @Override
    public Map<String, Map<String, String>> loadCompletedQuestData() {
        return doLoadData(completedStore);
    }

    private void doSaveData(File file, Map<String, Map<String, String>> data, String fileName) {
        try {
            file.getParentFile().mkdirs();

            if (file.exists()) {
                Path backup = file.toPath().getParent().resolve(fileName + ".bck");
                File backupFile = backup.toFile();
                backupFile.delete();
                backupFile.createNewFile();
                Files.copy(file.toPath(), backup);
                file.delete();
            }

            file.createNewFile();

            // todo write bson data to file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Map<String, String>> doLoadData(File file) {
        Map<String, Map<String, String>> result = new THashMap<>();

        if (!file.exists()) {
            // account for load being called before the first save
            return result;
        }

        // todo read bson data from file
        return result;
    }
}
