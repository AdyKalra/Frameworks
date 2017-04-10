package com.zapprx.comparatorator.inventories;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zapprx.comparatorator.inventories.FormFieldInventory.FieldType;

public class FormField {
    private final String path;
    private final String name;
    private final String parent;
    private final String type;
    private final String javaType;
    private final String value;
    private final List<String> values;

    public FormField(String path, String value) {
        this(path, null, null, null, null, value, null);
    }

    public FormField(String path, String name, String parent,
            String type, String javaType, String value, List<String> values) {
        super();
        this.path = path;
        this.name = name;
        this.parent = parent;
        this.type = type;
        this.javaType = javaType;
        this.value = value;
        this.values = values;
    }

    public String getPath() {
        return path;
    }

    public final String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public String getType() {
        return type;
    }

    public String getJavaType() {
        return javaType;
    }

    public String getValue() {
        return value;
    }

    public List<String> getValues() {
        return values;
    }

    public boolean isrootObject() {
        return getParent() == null;
    }

    public FieldType getFieldType() {
        final String type = getFieldTypeString();
        try {
            return FieldType.valueOf(type);
        } catch (final IllegalArgumentException e) {
            // zrx.error("Illegal FieldType " + type, e);
        }
        return FieldType.UNKNOWN;
    }

    public String getFieldTypeString() {
        String javaType = StringUtils
                .substringAfterLast(getJavaType(), ".");
        if (StringUtils.startsWith(javaType, "PD")) {
            javaType = StringUtils.substring(javaType, 2);
        }
        if (StringUtils.equalsIgnoreCase(javaType, "TEXTFIELD")) {
            javaType = "TEXTBOX";
        } else if (StringUtils.equalsIgnoreCase(javaType, "RADIOBUTTON")) {
            javaType = "RADIOCOLLECTION";
        }
        return StringUtils.upperCase(javaType);
    }

    @Override
    public String toString() {
        return "{" + path + ", " + name + ", " + parent + ", " + type
                + ", " + javaType + ", " + value + "}";
    }
}