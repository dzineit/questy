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
