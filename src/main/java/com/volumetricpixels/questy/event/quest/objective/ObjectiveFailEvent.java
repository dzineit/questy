package com.volumetricpixels.questy.event.quest.objective;

import com.volumetricpixels.questy.quest.QuestInstance;

public class ObjectiveFailEvent extends QuestObjectiveEvent {
    public ObjectiveFailEvent(QuestInstance quest) {
        super(quest);
    }
}
