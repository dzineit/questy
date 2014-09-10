package com.volumetricpixels.questy.event.quest;

import com.volumetricpixels.questy.quest.QuestInstance;

public class QuestStartEvent extends QuestEvent {
    // TODO: add causes for quest starting
    public QuestStartEvent(QuestInstance quest) {
        super(quest);
    }
}
