package com.volumetricpixels.questy.event.quest;

import com.volumetricpixels.questy.quest.QuestInstance;

public class QuestCompleteEvent extends QuestEvent {
    public QuestCompleteEvent(QuestInstance quest) {
        super(quest);
    }
}
