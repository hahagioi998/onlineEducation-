package com.hnguigu.domain.order.response;

import com.hnguigu.common.model.response.ResponseResult;
import com.hnguigu.common.model.response.ResultCode;
import com.hnguigu.domain.order.XcOrdersPay;
import lombok.Data;
import lombok.ToString;

/**
 * Created by mrt on 2018/3/27.
 */
@Data
@ToString
public class PayOrderResult extends ResponseResult {
    public PayOrderResult(ResultCode resultCode) {
        super(resultCode);
    }
    public PayOrderResult(ResultCode resultCode, XcOrdersPay xcOrdersPay) {
        super(resultCode);
        this.xcOrdersPay = xcOrdersPay;
    }
    private XcOrdersPay xcOrdersPay;
    private String orderNumber;

    //当tradeState为NOTPAY（未支付）时显示支付二维码
    private String codeUrl;
    private Float money;


}
