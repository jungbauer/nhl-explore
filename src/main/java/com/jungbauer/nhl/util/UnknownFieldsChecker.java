package com.jungbauer.nhl.util;

import com.google.gson.*;
import com.jungbauer.nhl.apidata.ClubSeasonSchedule;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * This is AI generated code that I want to play with later.
 */
//todo generate tests for this and use
public class UnknownFieldsChecker {

    public void basicCheck() {
        String json = "{ \"previousSeason\": 20242025, \"currentSeason\": 20252026, \"extraField\": 123 }";

        // Parse JSON to JsonObject
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        // Get all field names from the class
        Set<String> classFields = new HashSet<>();
        for (Field field : ClubSeasonSchedule.class.getDeclaredFields()) {
            classFields.add(field.getName());
        }

        // Find unknown fields
        for (String key : jsonObject.keySet()) {
            if (!classFields.contains(key)) {
                System.out.println("Unknown field: " + key);
            }
        }
    }

    public void recursiveCheck() {
        String json = "{ \"previousSeason\": 20242025, \"currentSeason\": 20252026, \"game\": { \"gameDate\": \"2025-10-08\", \"unknownNested\": 1 }, \"extraField\": 123 }";
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        checkUnknownFields(jsonObject, ClubSeasonSchedule.class, "");
    }

    public void checkUnknownFields(JsonObject jsonObject, Class<?> clazz, String path) {
        Set<String> classFields = new HashSet<>();
        for (Field field : clazz.getDeclaredFields()) {
            classFields.add(field.getName());
        }

        for (String key : jsonObject.keySet()) {
            if (!classFields.contains(key)) {
                System.out.println("Unknown field: " + path + key);
            } else {
                try {
                    Field classField = clazz.getDeclaredField(key);
                    Class<?> fieldType = classField.getType();
                    JsonElement element = jsonObject.get(key);

                    // If both are objects, recurse
                    if (element.isJsonObject() && !isPrimitiveOrWrapper(fieldType) && !fieldType.equals(String.class)) {
                        checkUnknownFields(element.getAsJsonObject(), fieldType, path + key + ".");
                    }
                } catch (NoSuchFieldException e) {
                    // Should not happen, as we already checked contains
                }
            }
        }
    }

    private static boolean isPrimitiveOrWrapper(Class<?> type) {
        return type.isPrimitive() ||
                type.equals(Boolean.class) ||
                type.equals(Byte.class) ||
                type.equals(Character.class) ||
                type.equals(Double.class) ||
                type.equals(Float.class) ||
                type.equals(Integer.class) ||
                type.equals(Long.class) ||
                type.equals(Short.class);
    }
}
