package com.volumetricpixels.questy.event.quest.objective;

import com.volumetricpixels.questy.quest.QuestInstance;

public class ObjectiveCompleteEvent extends QuestObjectiveEvent {
    // TODO: add the completion method - for example some objectives could have
    // multiple different ways of completing them
    public ObjectiveCompleteEvent(QuestInstance quest) {
        super(quest);
    }
}
