package com.shushan.manhua.entity.request;

import com.shushan.manhua.entity.constants.Constant;

public class LoginTouristModeRequest {
    public LoginTouristModeRequest() {
        this.platform = Constant.FROM;
    }

    public String deviceId;
    public String platform;
}
