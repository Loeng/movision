package com.movision.common.pojo;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 退款信息
 */
@ApiModel(value = "3333", description = "333")
public class RefundItem {

    /**
     * orderNo : 订单号
     * reason : 退款理由
     */

    @ApiModelProperty(value = "订单编号", required = true)
    private String orderNo;
    @ApiModelProperty(value = "退款理由 (不能有“^”、“|”、“$”、“#”等特殊字符)", required = true)
    private String reason;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
