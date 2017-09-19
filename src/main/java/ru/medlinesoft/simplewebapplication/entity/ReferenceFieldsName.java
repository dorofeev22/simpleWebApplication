package ru.medlinesoft.simplewebapplication.entity;

/**
 * Справочник типов сортировки.
 */
public enum ReferenceFieldsName {

    part_name, part_number, vendor, qty, shipped, receive;

    public static String getOrderSortByActualFields(
            String fieldsName, String parameter, String actualOrder) {
        if (fieldsName == null || actualOrder == null) {
            return ReferenceSortOrder.asc.name();
        }
        ReferenceFieldsName referenceFieldsName = createReferenceFieldsName(fieldsName);
        switch (referenceFieldsName) {
            case part_name:
                return parameter.equals("part_name_order") 
                        ? ReferenceSortOrder.inverseOrder(actualOrder) : ReferenceSortOrder.asc.name();
            case part_number:
                return parameter.equals("part_number_order") 
                        ? ReferenceSortOrder.inverseOrder(actualOrder) : ReferenceSortOrder.asc.name();
            default:
                return null;
        }
    }

    private static ReferenceFieldsName createReferenceFieldsName(String filedsName) {
        ReferenceFieldsName referenceFieldsName;
        try {
            referenceFieldsName = ReferenceFieldsName.valueOf(filedsName);
        } catch (IllegalArgumentException ex) {
            referenceFieldsName = null;
        }
        return referenceFieldsName;
    }


}
