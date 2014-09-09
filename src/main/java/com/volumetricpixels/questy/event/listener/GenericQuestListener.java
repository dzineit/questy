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
package com.volumetricpixels.questy.event.listener;

import com.volumetricpixels.questy.event.quest.QuestAbandonEvent;
import com.volumetricpixels.questy.event.quest.QuestCompleteEvent;
import com.volumetricpixels.questy.event.quest.QuestEvent;
import com.volumetricpixels.questy.event.quest.objective.QuestObjectiveEvent;
import com.volumetricpixels.questy.event.quest.QuestStartEvent;
import com.volumetricpixels.questy.event.quest.objective.ObjectiveCompleteEvent;
import com.volumetricpixels.questy.event.quest.objective.ObjectiveFailEvent;
import com.volumetricpixels.questy.event.quest.objective.ObjectiveStartEvent;

public abstract class GenericQuestListener implements QuestListener {
    public void onQuestEvent(QuestEvent event) {
    }

    public void onObjectiveEvent(QuestObjectiveEvent event) {
    }

    @Override
    public void questStarted(QuestStartEvent event) {
        onQuestEvent(event);
    }

    @Override
    public void questAbandoned(QuestAbandonEvent event) {
        onQuestEvent(event);
    }

    @Override
    public void questCompleted(QuestCompleteEvent event) {
        onQuestEvent(event);
    }

    @Override
    public void objectiveCompleted(ObjectiveCompleteEvent event) {
        onQuestEvent(event);
        onObjectiveEvent(event);
    }

    @Override
    public void objectiveFailed(ObjectiveFailEvent event) {
        onQuestEvent(event);
        onObjectiveEvent(event);
    }

    @Override
    public void objectiveStarted(ObjectiveStartEvent event) {
        onQuestEvent(event);
        onObjectiveEvent(event);
    }
}
