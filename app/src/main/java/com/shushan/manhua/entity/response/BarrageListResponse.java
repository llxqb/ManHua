package com.shushan.manhua.entity.response;

import java.util.List;

public class BarrageListResponse {


    /**
     * error : 0
     * msg : success
     * data : [{"barrage_id":6,"book_id":1,"catalogue_id":2,"barrage_content":"gxgdhd","barrage_time":1575343781,"style_id":0,"xcoord":"224.00","ycoord":"899.00","page":1}]
     */

    private int error;
    private String msg;
    private List<DataBean> data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * barrage_id : 6
         * book_id : 1
         * catalogue_id : 2
         * barrage_content : gxgdhd
         * barrage_time : 1575343781
         * style_id : 0
         * xcoord : 224.00
         * ycoord : 899.00
         * page : 1
         */

        private int barrage_id;
        private int book_id;
        private int catalogue_id;
        private String barrage_content;
        private int barrage_time;
        private int style_id;
        private String xcoord;
        private String ycoord;
        private int page;

        public int getBarrage_id() {
            return barrage_id;
        }

        public void setBarrage_id(int barrage_id) {
            this.barrage_id = barrage_id;
        }

        public int getBook_id() {
            return book_id;
        }

        public void setBook_id(int book_id) {
            this.book_id = book_id;
        }

        public int getCatalogue_id() {
            return catalogue_id;
        }

        public void setCatalogue_id(int catalogue_id) {
            this.catalogue_id = catalogue_id;
        }

        public String getBarrage_content() {
            return barrage_content;
        }

        public void setBarrage_content(String barrage_content) {
            this.barrage_content = barrage_content;
        }

        public int getBarrage_time() {
            return barrage_time;
        }

        public void setBarrage_time(int barrage_time) {
            this.barrage_time = barrage_time;
        }

        public int getStyle_id() {
            return style_id;
        }

        public void setStyle_id(int style_id) {
            this.style_id = style_id;
        }

        public String getXcoord() {
            return xcoord;
        }

        public void setXcoord(String xcoord) {
            this.xcoord = xcoord;
        }

        public String getYcoord() {
            return ycoord;
        }

        public void setYcoord(String ycoord) {
            this.ycoord = ycoord;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }
    }
}
