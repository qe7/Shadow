package me.shae.shadow.utils.config;

import com.google.gson.JsonObject;

/**
 * Marks objects that can be serialized to/from JSON.
 * Used for configuration and data persistence.
 */
public interface Serialized {

    /**
     * Converts the object to JSON representation.
     *
     * @return A JsonObject containing the object's data
     */
    JsonObject serialize();

    /**
     * Populates the object's fields from JSON data.
     *
     * @param object The JsonObject containing data to deserialize
     */
    void deserialize(JsonObject object);
}