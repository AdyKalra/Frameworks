package com.zapprx.comparatorator.inventories;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceEntry;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckbox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;
import org.apache.pdfbox.pdmodel.interactive.form.PDRadioButton;
import org.apache.pdfbox.pdmodel.interactive.form.PDSignatureField;

public class FormFieldInventory implements Inventory {
    // private static final RZapprxEnv zrx = RZapprxEnv.getZapprxEnv();
    private final int fieldcount;
    private final int pagecount;
    private final Map<String, FormField> map = new HashMap<String, FormField>();

    public enum FieldType {
        TEXTBOX, CHECKBOX, PUSHBUTTON, RADIOCOLLECTION, SIGNATUREFIELD, COMBOBOX, LISTBOX, CHOICE, UNKNOWN;
    }

    @SuppressWarnings("rawtypes")
    public FormFieldInventory(PDDocument pdfDocument) throws IOException {
        // this.pdfDocument = pdfDocument;
        this.pagecount = pdfDocument.getNumberOfPages();
        final PDDocumentCatalog docCatalog = pdfDocument.getDocumentCatalog();
        final PDAcroForm acroForm = docCatalog.getAcroForm();
        if (acroForm == null) {
            throw new IllegalArgumentException("Not A Fillable Form!");
        }
        final List fields = acroForm.getFields();
        this.fieldcount = fields.size();
        final Iterator fieldsIter = fields.iterator();

        while (fieldsIter.hasNext()) {
            final PDField field = (PDField) fieldsIter.next();
            mapField(field, field.getPartialName());
        }
    }

    private void mapField(PDField field, String sParent) throws IOException {
        List<String> values = null;
        if (field instanceof PDNonTerminalField) {
            final List<PDField> kids = ((PDNonTerminalField) field)
                    .getChildren();
            if (kids == null) {
                // zrx.debug("kids == null");
                return;
            }
            final Iterator<PDField> kidsIter = kids.iterator();
            if (!sParent.equals(field.getPartialName())) {
                sParent = sParent + "." + field.getPartialName();
            }

            values = new ArrayList<String>(kids.size());
            while (kidsIter.hasNext()) {
                final Object pdfObj = kidsIter.next();
                if (pdfObj instanceof PDField) {
                    final PDField kid = (PDField) pdfObj;
                    mapField(kid, sParent);
                    final String v = (kid instanceof PDCheckbox) ? getCheckBoxValue(
                            ((PDCheckbox) kid), 0) : kid.getPartialName();
                            values.add(v);
                }
            }
            final Object v = ((PDNonTerminalField) field).getValue();
            put(sParent, v != null ? v.toString() : "null", field, values);
        } else if (field instanceof PDSignatureField) {
            final PDSignatureField signatureField = (PDSignatureField) field;
            final PDSignature signature = signatureField.getSignature();
            final String signatureString = (signature != null) ? signature
                    .toString() : null;
                    // TODO fix this to map useful information
                    put(sParent, signatureString, field, values);
        } else {
            String key = sParent;
            String value = field.getValueAsString();
            if (field instanceof PDCheckbox) {
                values = new ArrayList<String>(2);
                final PDCheckbox checkbox = (PDCheckbox) field;
                final String onValue = getCheckBoxValue(checkbox, 0);
                values.add(onValue);
                values.add(getCheckBoxValue(checkbox, 1));
                key += "." + onValue;
                value = Boolean.toString(checkbox.isChecked());
            } else if (field instanceof PDRadioButton) {
                final PDRadioButton radiobutton = (PDRadioButton) field;
                values = getRadioButtonValues(radiobutton);
                // zrx.debug("RadioButton " + key + " values = " +
                // Arrays.toString(values.toArray()));
            }
            put(key, value, field, values);

        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.zapprx.comparatorator.Inventory#get(java.lang.String)
     */
    @Override
    public FormField get(String path) {
        return map.get(path);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.zapprx.comparatorator.Inventory#contains(java.lang.String)
     */
    @Override
    public boolean contains(String path) {
        return map.containsKey(path);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.zapprx.comparatorator.Inventory#keys()
     */
    @Override
    public Set<String> keys() {
        return map.keySet();
    }

    private void put(String path, String value, PDField field,
            List<String> values) throws IOException {
        while (contains(path)) {
            // zrx.debug("Duplicate entry " + path + " in map " + map);
            // throw new IllegalArgumentException("Duplicate entry " + path +
            // " in map " + map);
            // System.err.println("@@@@@ current entry " + path + " is " +
            // get(path) + " @@@@@");
            path += "&";
            // System.err.println("##### replacing entry " + path + " is " +
            // value + field.getPartialName() + field.getClass().getName() +
            // field.getParent() + " ");
        }
        final String javaType = field.getClass().getName();
        final String type = field.getFieldType();
        final String name = field.getPartialName();
        final PDField parent = field.getParent();
        final String parentName = parent != null ? parent
                .getFullyQualifiedName() : null;
                map.put(path, new FormField(path, name, parentName, type, javaType,
                        value, values));
    }

    public int getFieldcount() {
        return fieldcount;
    }

    public int getPagecount() {
        return pagecount;
    }

    public static Inventory inventoryFieldsFromInputStream(InputStream is)
            throws IOException {
        PDDocument pdfFile = null;
        Inventory inventory = null;
        try {
            pdfFile = PDDocument.load(is);
            inventory = new FormFieldInventory(pdfFile);
        } finally {
            if (pdfFile != null) {
                pdfFile.close();
            }
        }
        return inventory;
    }

    public static void printDocumentInventory(PDDocument pdfFile, PrintStream ps)
            throws IOException {
        final FormFieldInventory inventory = new FormFieldInventory(pdfFile);
        ps.println("fieldcount = " + inventory.fieldcount);
        ps.println("pagecount = " + inventory.pagecount);
        for (final String path : inventory.keys()) {
            ps.println(path + " = " + inventory.get(path));
        }
    }

    public static String[] getCheckBoxValues(PDCheckbox checkbox) {
        // List<String> opts = checkbox.getOptions();
        final String[] a = { "" };
        // if (opts == null) {
        // return null;
        // }
        // return opts.toArray(a);
        return a;
    }

    public static String getCheckBoxValue(PDCheckbox checkbox, int index) {
        final String opts[] = getCheckBoxValues(checkbox);
        // System.out.println("Checkbox " + checkbox.getFullyQualifiedName() +
        // " index is " + index + " opts.length " + opts.length);
        if (opts == null || index >= opts.length || opts[index] == null) {
            if (index < 2) {
                return index == 0 ? "On" : "Off";
            } else {
                return null;
            }
        }
        return opts[index];
    }

    public static List<String> getRadioButtonValues(PDRadioButton radiobutton) {
        final List<PDAnnotationWidget> opts = radiobutton.getWidgets();
        final List<String> buttons = new ArrayList<String>();
        for (final PDAnnotationWidget opt : opts) {
            final PDAppearanceEntry appearance = opt.getAppearance()
                    .getNormalAppearance();
            final Set<COSName> cosnames = (((COSDictionary) appearance
                    .getCOSObject()).keySet());
            for (final COSName cosname : cosnames) {
                if (!"Off".equals(cosname.getName())) {
                    final String buttonName = cosname.getName();
                    buttons.add(buttonName);
                    // buttons.add(opt.getAnnotationName());
                }
            }
        }
        @SuppressWarnings("unused")
        final String[] a = { "" };
        return buttons;
    }
}
