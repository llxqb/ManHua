package com.shushan.manhua.entity.request;

import com.shushan.manhua.entity.constants.Constant;

public class PaySwitchRequest {
    public PaySwitchRequest() {
        platform = Constant.FROM;
    }

    public String token;
    public String version;
    public String platform;
}
