package ru.dorofeev22.simplewebapplication.entity;

import java.sql.Date;

/**
 * Part entity.
 */
public class Part {

    private Integer id;
    private String partName;
    private String partNumber;
    private String vendor;
    private Integer qty;
    private Date shipped;
    private Date receive;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

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
    public Integer getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * @return the shipped
     */
    public Date getShipped() {
        return shipped;
    }

    /**
     * @param shipped the shipped to set
     */
    public void setShipped(Date shipped) {
        this.shipped = shipped;
    }

    /**
     * @return the receive
     */
    public Date getReceive() {
        return receive;
    }

    /**
     * @param receive the receive to set
     */
    public void setReceive(Date receive) {
        this.receive = receive;
    }

    
}
