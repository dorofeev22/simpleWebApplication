package ru.medlinesoft.simplewebapplication.model;

import java.util.Map;
import ru.medlinesoft.simplewebapplication.domain.SearchParameterName;

/**
 * Массив параметров запроса.
 */
public class PartParameters {

    public PartParameters(
            String order, 
            String orderedFieldName, 
            Map<SearchParameterName, String> searchParameters) {
        this.order = order;
        this.orderedFieldName = orderedFieldName;
        this.searchParameters = searchParameters;
    }

    private String order;
    private String orderedFieldName;
    private Map<SearchParameterName, String> searchParameters;

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
    public Map<SearchParameterName, String> getSearchParameters() {
        return searchParameters;
    }

    /**
     * @param searchParameters the searchParameters to set
     */
    public void setSearchParameters(Map<SearchParameterName, String> searchParameters) {
        this.searchParameters = searchParameters;
    }
    
    
}
