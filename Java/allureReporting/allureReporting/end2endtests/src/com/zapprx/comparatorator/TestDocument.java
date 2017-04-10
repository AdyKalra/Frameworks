/**
 *
 */
package com.zapprx.comparatorator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;

import com.zapprx.comparatorator.inventories.FormFieldInventory;
import com.zapprx.comparatorator.inventories.Inventory;
import com.zapprx.comparatorator.inventories.JsonInventory;

/**
 * An abstract document that can be compared. The data source can be a file or
 * an input stream. Current allowed data formats are PDF and JSON.
 *
 * @author ejw
 *
 */
public class TestDocument {
    private final InputStream input;
    private final String name;
    private final DocumentType type;
    private transient Inventory inventory = null;

    private static enum DocumentType {
        PDF, JSON;

        public static DocumentType getType(String typeName) {
            return typeName != null ? DocumentType.valueOf(typeName
                    .toUpperCase()) : null;
        }
    }

    /**
     * @param file
     * @throws FileNotFoundException
     */
    public TestDocument(File file) throws FileNotFoundException {
        this(new FileInputStream(file), file.getName());
    }

    /**
     * @param input
     * @param fileName
     */
    public TestDocument(InputStream input, String fileName) {
        // final String fileName =;
        this.type = parseType(fileName);
        this.name = parseName(fileName);
        this.input = input;
    }

    /**
     * @param input
     * @param name
     * @param type
     */
    public TestDocument(InputStream input, String name, DocumentType type) {
        this.input = input;
        this.name = name;
        this.type = type;
    }

    /**
     * @return
     * @throws IOException
     */
    public Inventory extractInventory() throws IOException {
        if (inventory != null) {
            return inventory;
        }

        try (final BufferedInputStream bis = new BufferedInputStream(input)) {
            switch (type) {
            case PDF:
                return extractInventoryFromPDF(bis);
            case JSON:
                return extractInventoryFromJSON(bis);
            }
        } finally {
            input.close();
        }
        return null;
    }

    private String parseName(String fileName) {
        final String[] components = StringUtils.split(fileName, '.');
        return components != null && components.length > 0 ? components[0]
                : null;
    }

    private DocumentType parseType(String fileName) {
        final String[] components = StringUtils.split(fileName, '.');
        return components != null && components.length > 1 ? DocumentType
                .getType(components[1]) : null;
    }

    private Inventory extractInventoryFromPDF(BufferedInputStream bis)
            throws IOException {
        try (PDDocument pdf = PDDocument.load(bis)) {
            return new FormFieldInventory(pdf);
        }
    }

    private Inventory extractInventoryFromJSON(BufferedInputStream bis)
            throws IOException {
        final String jsonString = IOUtils.toString(bis, "UTF-8");
        return new JsonInventory(jsonString);
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public DocumentType getType() {
        return type;
    }
}
