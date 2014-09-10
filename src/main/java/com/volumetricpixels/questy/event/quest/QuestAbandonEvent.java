package com.volumetricpixels.questy.event.quest;

import com.volumetricpixels.questy.quest.QuestInstance;

public class QuestAbandonEvent extends QuestEvent {
    public QuestAbandonEvent(QuestInstance quest) {
        super(quest);
    }
}
