package com.tusdao.webservice.model;

import java.util.Date;

public class CatalogInfo {
    private Integer id;

    private String dataId;

    private String dataName;

    private String contractId;

    private String taskInitiator;

    private Date createTime;

    private String wsMethod;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId == null ? null : dataId.trim();
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName == null ? null : dataName.trim();
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getTaskInitiator() {
        return taskInitiator;
    }

    public void setTaskInitiator(String taskInitiator) {
        this.taskInitiator = taskInitiator == null ? null : taskInitiator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getWsMethod() {
        return wsMethod;
    }

    public void setWsMethod(String wsMethod) {
        this.wsMethod = wsMethod == null ? null : wsMethod.trim();
    }
}