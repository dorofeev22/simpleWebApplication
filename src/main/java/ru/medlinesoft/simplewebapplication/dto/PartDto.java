package ru.medlinesoft.simplewebapplication.dto;

/**
 * Модель представления Part в списке.
 */
public class PartDto {

    private String partName;
    private String partNumber;
    private String vendor;
    private String qty;
    private String shipped;
    private String receive;

    /**
     * @return the partName
     */
    public String getPartName() {
        return partName;
    }

    /**
     * @param partName the partName to set
     */
    public void setPartName(String partName) {
        this.partName = partName;
    }

    /**
     * @return the partNumber
     */
    public String getPartNumber() {
        return partNumber;
    }

    /**
     * @param partNumber the partNumber to set
     */
    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    /**
     * @return the vendor
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * @param vendor the vendor to set
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * @return the qty
     */
    public String getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(String qty) {
        this.qty = qty;
    }

    /**
     * @return the shipped
     */
    public String getShipped() {
        return shipped;
    }

    /**
     * @param shipped the shipped to set
     */
    public void setShipped(String shipped) {
        this.shipped = shipped;
    }

    /**
     * @return the receive
     */
    public String getReceive() {
        return receive;
    }

    /**
     * @param receive the receive to set
     */
    public void setReceive(String receive) {
        this.receive = receive;
    }
}
