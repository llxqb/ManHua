package com.shushan.manhua.entity.response;

import java.util.List;

public class BuyBarrageStyleResponse {

    private List<StyleBean> style;

    public List<StyleBean> getStyle() {
        return style;
    }

    public void setStyle(List<StyleBean> style) {
        this.style = style;
    }

    public static class StyleBean {
        /**
         * style_id : 1
         * end_time : 11111111
         */

        private int style_id;
        private int end_time;

        public int getStyle_id() {
            return style_id;
        }

        public void setStyle_id(int style_id) {
            this.style_id = style_id;
        }

        public int getEnd_time() {
            return end_time;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }
    }
}
