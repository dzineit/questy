package com.volumetricpixels.questy.event.quest.objective;

import com.volumetricpixels.questy.quest.QuestInstance;
import com.volumetricpixels.questy.event.quest.QuestEvent;

public abstract class QuestObjectiveEvent extends QuestEvent {
    // TODO: add objectives
    protected QuestObjectiveEvent(QuestInstance quest) {
        super(quest);
    }
}
