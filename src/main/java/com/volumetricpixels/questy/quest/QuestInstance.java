package com.volumetricpixels.questy.quest;

/**
 * Represents an 'instance' of a {@link Quest}. A {@link QuestInstance} holds
 * the {@link Quest} object it is an instance of, but also holds the player
 * doing the quest and data about said player's progression through the quest.
 */
public final class QuestInstance {
    private final Quest quest;
    private final String quester;

    // TODO: Progression data

    public QuestInstance(Quest quest, String quester) {
        this.quest = quest;
        this.quester = quester;
    }

    public Quest getQuest() {
        return quest;
    }

    public String getQuester() {
        return quester;
    }

    // TODO: progression data based methods

    public String serializeProgression() {
        // TODO
        return "";
    }

    public static QuestInstance deserialize(Quest quest, String quester,
            String progression) {
        QuestInstance inst = new QuestInstance(quest, quester);
        // TODO: set progression data fields based on serialized data
        return inst;
    }
}
