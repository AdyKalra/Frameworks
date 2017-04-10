package com.zapprx.comparatorator.inventories;

import java.util.Set;

import org.json.JSONObject;

public class JsonInventory implements Inventory {
    private final JSONObject json;

    public JsonInventory(String jsonString) {
        final JSONObject json = new JSONObject(jsonString);
        this.json = json;
    }

    public JsonInventory(JSONObject json) {
        this.json = json;
    }

    @Override
    public FormField get(String path) {
        if (!json.has(path)) {
            return null;
        }

        final String value = json.getString(path);
        return new FormField(path, value);
    }

    @Override
    public boolean contains(String path) {
        return json.has(path);
    }

    @Override
    public Set<String> keys() {
        final Set<String> keys = json.keySet();
        return keys;
    }

}
