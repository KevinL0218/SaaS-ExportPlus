package cn.itcast.domain.cargo;

import cn.itcast.domain.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class Packing extends BaseEntity implements Serializable {
    private String packingListId;

    private String exportIds;

    private String exportNos;

    private Date packingTime;

    private Double totalVolume;

    private Double netWeights;

    private Double grossWeights;

    private String marks;

    private String description;

    private Double packingMoney;

    private Integer state;

    public String getPackingListId() {
        return packingListId;
    }

    public void setPackingListId(String packingListId) {
        this.packingListId = packingListId == null ? null : packingListId.trim();
    }

    public String getExportIds() {
        return exportIds;
    }

    public void setExportIds(String exportIds) {
        this.exportIds = exportIds == null ? null : exportIds.trim();
    }

    public String getExportNos() {
        return exportNos;
    }

    public void setExportNos(String exportNos) {
        this.exportNos = exportNos == null ? null : exportNos.trim();
    }

    public Date getPackingTime() {
        return packingTime;
    }

    public void setPackingTime(Date packingTime) {
        this.packingTime = packingTime;
    }

    public Double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(Double totalVolume) {
        this.totalVolume = totalVolume;
    }

    public Double getNetWeights() {
        return netWeights;
    }

    public void setNetWeights(Double netWeights) {
        this.netWeights = netWeights;
    }

    public Double getGrossWeights() {
        return grossWeights;
    }

    public void setGrossWeights(Double grossWeights) {
        this.grossWeights = grossWeights;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks == null ? null : marks.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Double getPackingMoney() {
        return packingMoney;
    }

    public void setPackingMoney(Double packingMoney) {
        this.packingMoney = packingMoney;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state == null ? 0 : state;
    }
}