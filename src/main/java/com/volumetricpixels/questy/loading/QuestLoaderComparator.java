/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.loading;

import java.util.Comparator;

/**
 * Compares {@link QuestLoader}s based on their format.
 */
public class QuestLoaderComparator implements Comparator<QuestLoader> {
    public static final QuestLoaderComparator INSTANCE = new QuestLoaderComparator();

    @Override
    public int compare(QuestLoader o1, QuestLoader o2) {
        return o1.compareTo(o2);
    }
}
