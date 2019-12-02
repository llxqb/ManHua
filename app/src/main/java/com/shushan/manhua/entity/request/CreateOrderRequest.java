package com.shushan.manhua.entity.request;

import com.shushan.manhua.entity.constants.Constant;

/**
 * 创建订单
 */
public class CreateOrderRequest {
    public CreateOrderRequest() {
        from = Constant.FROM;
    }

    public String token;
    /**
     * 1购买会员2购买金币
     */
    public String type;
    /**
     * 会员规则或者金币规则id
     */
    public String relation_id;
    /**
     * 订单金额
     */
    public String money;
    /**
     * 来源
     */
    public String from;
}
