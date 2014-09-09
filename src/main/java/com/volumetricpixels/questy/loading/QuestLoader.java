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
package com.volumetricpixels.questy.loading;

import com.volumetricpixels.questy.quest.Quest;

import java.io.File;
import java.util.Set;

/**
 * Loads scripted / configured quests. Quests can be defined in any format, but
 * there must be a registered {@link QuestLoader} implementation for the format
 * for it to be loaded.
 */
public interface QuestLoader {
    /**
     * Loads all of the {@link Quest}s that this {@link QuestLoader} can handle
     * which are located in the given {@link File}, which should be a directory,
     * and should exist. Note that this method should <em>NOT</em> throw an
     * exception if there is a file in the given directory of a format which
     * this {@link QuestLoader} cannot load.
     *
     * @param directory the directory to load {@link Quest}s from
     * @return all {@link Quest}s loaded from the given directory
     */
    Set<Quest> loadQuests(File directory);

    /**
     * Gets the name of the format this {@link QuestLoader} loads {@link Quest}s
     * in. For example, in a JavaScript {@link QuestLoader} this method would
     * return "JavaScript".
     *
     * @return the name of the format this loader loads {@link Quest}s of
     */
    String getQuestFormat();
}
