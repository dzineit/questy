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
