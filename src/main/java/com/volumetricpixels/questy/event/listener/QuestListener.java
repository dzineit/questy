package com.volumetricpixels.questy.event.listener;

import com.volumetricpixels.questy.event.quest.QuestAbandonEvent;
import com.volumetricpixels.questy.event.quest.QuestCompleteEvent;
import com.volumetricpixels.questy.event.quest.QuestEvent;
import com.volumetricpixels.questy.event.quest.QuestStartEvent;
import com.volumetricpixels.questy.event.quest.objective.ObjectiveCompleteEvent;
import com.volumetricpixels.questy.event.quest.objective.ObjectiveFailEvent;
import com.volumetricpixels.questy.event.quest.objective.ObjectiveStartEvent;

/**
 * Used for listening to {@link QuestEvent}s. Note that implementation of this
 * interface requires a method for each and every {@link QuestEvent} - if you
 * only want one or to you should instead use {@link BaseQuestListener}, or
 * {@link GenericQuestListener} if you want to listen for every type of {@link
 * QuestEvent} from one method.
 */
public interface QuestListener {
    // TODO: JavaDoc
    void questStarted(QuestStartEvent event);

    void questAbandoned(QuestAbandonEvent event);

    void questCompleted(QuestCompleteEvent event);

    void objectiveCompleted(ObjectiveCompleteEvent event);

    void objectiveFailed(ObjectiveFailEvent event);

    void objectiveStarted(ObjectiveStartEvent event);
}
