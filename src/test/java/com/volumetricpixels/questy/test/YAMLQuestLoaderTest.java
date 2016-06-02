/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.test;

import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import com.volumetricpixels.questy.questy.SimpleQuestManager;
import com.volumetricpixels.questy.questy.loader.YMLQuestLoader;
import com.volumetricpixels.questy.Quest;
import com.volumetricpixels.questy.objective.Objective;
import com.volumetricpixels.questy.objective.Outcome;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class YAMLQuestLoaderTest {
    @Test
    public void runTest() {
        try {
            SimpleQuestManager mgr = new SimpleQuestManager(null);
            YMLQuestLoader loader = new YMLQuestLoader(mgr);
            String yaml = generateYamlQuest();
            Yaml yamlObj = new Yaml();
            InputStream stream = new ByteArrayInputStream(
                    yaml.getBytes("UTF-8"));
            Quest quest = loader.loadQuest(yamlObj, stream);
            check(quest);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String generateYamlQuest() {
        StringBuilder builder = new StringBuilder();
        builder.append("name: BobQuest\n");
        builder.append("description: Help Bob!\n");
        builder.append("objectives:\n");
        indent(builder, 1).append("Run:\n");
        indent(builder, 2).append("description: Run away!\n");
        indent(builder, 2).append("outcomes:\n");
        indent(builder, 3).append("death:\n");
        indent(builder, 4).append("description: Bob kills you\n");
        indent(builder, 4).append("type: lel\n");
        return builder.toString();
    }


    private void check(Quest built) {
        Assert.assertTrue(built.getName().equals("BobQuest"));
        Assert.assertTrue(built.getDescription().equals("Help Bob!"));

        Objective run = built.getObjective("Run");
        Assert.assertNotNull(run);

        Assert.assertEquals("Run away!", run.getDescription());

        Outcome death = run.getOutcome("death");
        Assert.assertNotNull(death);

        Assert.assertEquals("Bob kills you", death.getDescription());
        Assert.assertEquals("lel", death.getType());
    }

    private StringBuilder indent(StringBuilder b, int amt) {
        for (int i = 0; i < amt; i++) {
            b.append("  ");
        }
        return b;
    }
}
