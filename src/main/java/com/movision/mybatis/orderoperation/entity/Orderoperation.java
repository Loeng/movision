package com.movision.mybatis.orderoperation.entity;

import java.io.Serializable;
import java.util.Date;

public class Orderoperation implements Serializable {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String orderoperation;



    private Date orderoperationtime;

    private Integer orderstatue;


    private Integer logisticstatue;

    private Integer orderid;

    private String remark;

    public String getOrderoperation() {
        return orderoperation;
    }

    public void setOrderoperation(String orderoperation) {
        this.orderoperation = orderoperation == null ? null : orderoperation.trim();
    }

    public Date getOrderoperationtime() {
        return orderoperationtime;
    }

    public void setOrderoperationtime(Date orderoperationtime) {
        this.orderoperationtime = orderoperationtime;
    }

    public Integer getOrderstatue() {
        return orderstatue;
    }

    public void setOrderstatue(Integer orderstatue) {
        this.orderstatue = orderstatue;
    }


    public Integer getLogisticstatue() {
        return logisticstatue;
    }

    public void setLogisticstatue(Integer logisticstatue) {
        this.logisticstatue = logisticstatue;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}