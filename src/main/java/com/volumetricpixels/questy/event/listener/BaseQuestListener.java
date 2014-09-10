package com.volumetricpixels.questy.event.listener;

import com.volumetricpixels.questy.event.quest.QuestAbandonEvent;
import com.volumetricpixels.questy.event.quest.QuestCompleteEvent;
import com.volumetricpixels.questy.event.quest.QuestEvent;
import com.volumetricpixels.questy.event.quest.QuestStartEvent;
import com.volumetricpixels.questy.event.quest.objective.ObjectiveCompleteEvent;
import com.volumetricpixels.questy.event.quest.objective.ObjectiveFailEvent;
import com.volumetricpixels.questy.event.quest.objective.ObjectiveStartEvent;

/**
 * A {@link QuestListener} which contains no implementation but instead
 * overrides all of the listener methods to allow for people to only listen for
 * select {@link QuestEvent}s.
 */
public abstract class BaseQuestListener implements QuestListener {
    @Override
    public void questStarted(QuestStartEvent event) {
    }

    @Override
    public void questAbandoned(QuestAbandonEvent event) {
    }

    @Override
    public void questCompleted(QuestCompleteEvent event) {
    }

    @Override
    public void objectiveCompleted(ObjectiveCompleteEvent event) {
    }

    @Override
    public void objectiveFailed(ObjectiveFailEvent event) {
    }

    @Override
    public void objectiveStarted(ObjectiveStartEvent event) {
    }
}
