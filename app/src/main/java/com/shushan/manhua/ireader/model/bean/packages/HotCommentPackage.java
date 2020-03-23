package com.shushan.manhua.ireader.model.bean.packages;


import com.shushan.manhua.ireader.model.bean.BaseBean;
import com.shushan.manhua.ireader.model.bean.HotCommentBean;

import java.util.List;

/**
 * Created by newbiechen on 17-5-4.
 */

public class HotCommentPackage extends BaseBean {

    private List<HotCommentBean> reviews;

    public List<HotCommentBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<HotCommentBean> reviews) {
        this.reviews = reviews;
    }
}
