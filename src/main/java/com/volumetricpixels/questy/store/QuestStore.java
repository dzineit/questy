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

import java.util.Map;

/**
 * Stores data about the progression of players into quests and quest chains.
 */
public abstract class QuestStore {
    /**
     * Stores the given quest progression {@code data} for the given {@code
     * player}. The {@code data} parameter is a {@link Map} of quest names to
     * serialized progression data for the quest.
     *
     * @param data the data to store
     */
    public abstract void saveCurrentQuestData(
            Map<String, Map<String, String>> data);

    /**
     * Loads all stored quest progression data from the data store.
     *
     * @return a {@link Map} of player names to stored quest progression data
     */
    public abstract Map<String, Map<String, String>> loadCurrentQuestData();

    /**
     * Stores the given quest progression {@code data} for the given {@code
     * player}. The {@code data} parameter is a {@link Map} of quest names to
     * serialized progression data for the quest.
     *
     * @param data the data to store
     */
    public abstract void saveCompletedQuestData(
            Map<String, Map<String, String>> data);

    /**
     * Loads all stored quest progression data from the data store.
     *
     * @return a {@link Map} of player names to stored quest progression data
     */
    public abstract Map<String, Map<String, String>> loadCompletedQuestData();
}
