package com.volumetricpixels.questy.loading;

import java.util.Comparator;

/**
 * Compares {@link QuestLoader}s based on their format.
 */
public class QuestLoaderComparator implements Comparator<QuestLoader> {
    @Override
    public int compare(QuestLoader o1, QuestLoader o2) {
        return o1.getQuestFormat().compareTo(o2.getQuestFormat());
    }
}
