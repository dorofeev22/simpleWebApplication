package ru.medlinesoft.simplewebapplication.model;

import java.util.Map;

/**
 * Массив параметров запроса.
 */
public class PartParameters {

    public PartParameters(String order, String orderedFieldName, Map<String, String> searchParameters) {
        this.order = order;
        this.orderedFieldName = orderedFieldName;
        this.searchParameters = searchParameters;
    }

    private String order;
    private String orderedFieldName;
    private Map<String, String> searchParameters;

    /**
     * @return the order
     */
    public String getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * @return the orderedFieldName
     */
    public String getOrderedFieldName() {
        return orderedFieldName;
    }

    /**
     * @param orderedFieldName the orderedFieldName to set
     */
    public void setOrderedFieldName(String orderedFieldName) {
        this.orderedFieldName = orderedFieldName;
    }

    /**
     * @return the searchParameters
     */
    public Map<String, String> getSearchParameters() {
        return searchParameters;
    }

    /**
     * @param searchParameters the searchParameters to set
     */
    public void setSearchParameters(Map<String, String> searchParameters) {
        this.searchParameters = searchParameters;
    }
    
    
}
