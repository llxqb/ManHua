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
         * id : 4
         * user_id : 19
         * style_id : 3
         * end_time : 1578202023
         * exchange_time : 1575610023
         */

        private int id;
        private int user_id;
        private int style_id;
        private int end_time;
        private int exchange_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

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

        public int getExchange_time() {
            return exchange_time;
        }

        public void setExchange_time(int exchange_time) {
            this.exchange_time = exchange_time;
        }
    }
}
