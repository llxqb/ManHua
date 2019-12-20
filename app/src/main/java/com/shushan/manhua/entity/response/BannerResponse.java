package com.shushan.manhua.entity.response;

import com.shushan.manhua.entity.BannerBean;

import java.util.List;

public class BannerResponse {

    private List<BannerBean> banner;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

}
