package ru.medlinesoft.simplewebapplication.entity;

/**
 * Справочник типов сортировки.
 */
public enum ReferenceSortOrder {

    asc, desc;

    /**
     * Инверсия текущего порядка сортировки из строкового значения.
     * @param inputOrder
     * @return 
     */
    public static String inverseOrder(String inputOrder) {
        ReferenceSortOrder order = inputOrder != null ? createSortOrder(inputOrder) : asc;
        return order.equals(ReferenceSortOrder.asc) ? 
                ReferenceSortOrder.desc.name() : ReferenceSortOrder.asc.name();
    }

    /**
     * Формирование справочного значения из строки.
     * @param inputOrder
     * @return 
     */
    private static ReferenceSortOrder createSortOrder(String inputOrder) {
        ReferenceSortOrder sortOrder;
        try {
            sortOrder = ReferenceSortOrder.valueOf(inputOrder);
        } catch (IllegalArgumentException ex) {
            sortOrder = null;
        }
        return sortOrder;
    }

}
