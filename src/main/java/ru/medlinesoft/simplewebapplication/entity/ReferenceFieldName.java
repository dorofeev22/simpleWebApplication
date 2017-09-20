package ru.medlinesoft.simplewebapplication.entity;

/**
 * Справочник типов сортировки.
 */
public enum ReferenceFieldName {

    part_name, part_number, vendor, qty, shipped, receive;

    public static String getOrderSortByActualFields(
            String fieldsName, String parameter, String actualOrder) {
        if (fieldsName == null || actualOrder == null) {
            return ReferenceSortOrder.asc.name();
        }
        ReferenceFieldName referenceFieldsName = createReferenceFieldsName(fieldsName);
        switch (referenceFieldsName) {
            case part_name:
                return parameter.equals("part_name_order") 
                        ? ReferenceSortOrder.inverseOrder(actualOrder) : ReferenceSortOrder.asc.name();
            case part_number:
                return parameter.equals("part_number_order") 
                        ? ReferenceSortOrder.inverseOrder(actualOrder) : ReferenceSortOrder.asc.name();
            case vendor:
                return parameter.equals("vendor_order") 
                        ? ReferenceSortOrder.inverseOrder(actualOrder) : ReferenceSortOrder.asc.name();
            case qty:
                return parameter.equals("qty_order") 
                        ? ReferenceSortOrder.inverseOrder(actualOrder) : ReferenceSortOrder.asc.name();
            case shipped:
                return parameter.equals("shipped_order") 
                        ? ReferenceSortOrder.inverseOrder(actualOrder) : ReferenceSortOrder.asc.name();
            case receive:
                return parameter.equals("receive_order") 
                        ? ReferenceSortOrder.inverseOrder(actualOrder) : ReferenceSortOrder.asc.name();
            default:
                return null;
        }
    }

    private static ReferenceFieldName createReferenceFieldsName(String filedsName) {
        ReferenceFieldName referenceFieldsName;
        try {
            referenceFieldsName = ReferenceFieldName.valueOf(filedsName);
        } catch (IllegalArgumentException ex) {
            referenceFieldsName = null;
        }
        return referenceFieldsName;
    }


}
