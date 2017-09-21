package ru.medlinesoft.simplewebapplication.entity;

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


}
