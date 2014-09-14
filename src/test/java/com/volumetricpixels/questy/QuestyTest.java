/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy;

import org.junit.Test;

public class QuestyTest {
    private final QuestManager questManager = new QuestManager(null);

    @Test
    public void testEvents() {
        new EventsTest().testEvents(questManager);
    }
}
