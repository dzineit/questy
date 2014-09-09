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
package com.volumetricpixels.questy.event.quest;

import com.volumetricpixels.questy.quest.Quest;
import com.volumetricpixels.questy.quest.QuestInstance;
import com.volumetricpixels.questy.event.QuestyEvent;

/**
 * Represents a {@link Quest}-related event. Used for listening for many things
 * which happen during a {@link Quest}, which can be hooked into to perform any
 * action.
 */
public abstract class QuestEvent extends QuestyEvent {
    /**
     * The {@link QuestInstance} this {@link QuestEvent} relates to.
     */
    private final QuestInstance quest;

    protected QuestEvent(QuestInstance quest) {
        this.quest = quest;
    }

    public QuestInstance getQuest() {
        return quest;
    }

    public Quest getBaseQuest() {
        return getQuest().getQuest();
    }

    public String getQuester() {
        return getQuest().getQuester();
    }
}
