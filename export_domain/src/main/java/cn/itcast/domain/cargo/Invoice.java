package cn.itcast.domain.cargo;

import cn.itcast.domain.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class Invoice extends BaseEntity implements Serializable {
    private String id;

    private String scNo;

    private String tradeTerms;

    private String money;

    private String invoiceStatus;

    private String pickupPhoto;

    private String createBy;

    private String createDept;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getScNo() {
        return scNo;
    }

    public void setScNo(String scNo) {
        this.scNo = scNo == null ? null : scNo.trim();
    }

    public String getTradeTerms() {
        return tradeTerms;
    }

    public void setTradeTerms(String tradeTerms) {
        this.tradeTerms = tradeTerms == null ? null : tradeTerms.trim();
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money == null ? null : money.trim();
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus == null ? null : invoiceStatus.trim();
    }

    public String getPickupPhoto() {
        return pickupPhoto;
    }

    public void setPickupPhoto(String pickupPhoto) {
        this.pickupPhoto = pickupPhoto == null ? null : pickupPhoto.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getCreateDept() {
        return createDept;
    }

    public void setCreateDept(String createDept) {
        this.createDept = createDept == null ? null : createDept.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}