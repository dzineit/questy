/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.volumetricpixels.questy.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class DeserializeUtils {
    private static final List<Function<String, Object>> common = new ArrayList<>();

    static {
        addCommonTypeHandler((str) -> {
            try {
                return Integer.parseInt(str);
            } catch (Exception e) {
                return null;
            }
        });

        addCommonTypeHandler((str) -> {
            try {
                return Double.parseDouble(str);
            } catch (Exception e) {
                return null;
            }
        });

        addCommonTypeHandler((str) -> {
            if (str.length() == 1) {
                return str.charAt(0);
            }
            return null;
        });
    }

    /**
     * Adds the given {@link Function} which takes a {@link String} and produces
     * any {@link Object} to the {@link List} of common types. The given {@link
     * Function} will be assigned highest priority - i.e it is inserted at the
     * top of the {@link List}.
     *
     * @param func the {@link Function} to add
     */
    public static void addCommonTypeHandler(Function<String, Object> func) {
        common.add(0, func);
    }

    public static Object handleCommonTypes(String serialized) {
        for (Function<String, Object> func : common) {
            Object result = func.apply(serialized);
            if (result != null) {
                return result;
            }
        }
        return serialized;
    }

    public static void writeObject(File file, Object object) {
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readObject(File file) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object result = in.readObject();
            in.close();
            fileIn.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @deprecated do not call
     */
    @Deprecated
    private DeserializeUtils() {
        throw new UnsupportedOperationException();
    }
}
