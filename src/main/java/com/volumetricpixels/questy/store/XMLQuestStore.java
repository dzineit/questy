/*
 * This file is part of Questy, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2014 VolumetricPixels <http://volumetricpixels.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
