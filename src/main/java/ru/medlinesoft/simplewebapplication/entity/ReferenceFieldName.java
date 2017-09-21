package ru.medlinesoft.simplewebapplication.entity;

import java.util.Arrays;
import java.util.List;

/**
 * Справочник типов сортировки.
 */
public enum ReferenceFieldName {

    part_name, part_number, vendor, qty, shipped, receive;

    public static ReferenceFieldName createReferenceFieldsName(String filedsName) {
        ReferenceFieldName referenceFieldsName;
        try {
            referenceFieldsName = ReferenceFieldName.valueOf(filedsName);
        } catch (IllegalArgumentException ex) {
            referenceFieldsName = null;
        }
        return referenceFieldsName;
    }

    public static String[] getFieldNames() {
        List<ReferenceFieldName> referenceFieldNames = Arrays.asList(ReferenceFieldName.values());
        String[] fieldNames = new String[referenceFieldNames.size()];
        for (ReferenceFieldName fieldName : referenceFieldNames) {
            fieldNames[referenceFieldNames.indexOf(fieldName)] = fieldName.name();
        }
        return fieldNames;
    }
}
