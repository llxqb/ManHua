package com.shushan.manhua.entity.response;

import java.util.List;

public class MessageResponse {

    /**
     * error : 0
     * msg : success
     * data : [{"id":1,"user_id":54,"msg_id":1,"send_time":1575364051,"status":1,"state":2,"title":"测试","content":"测试内容","system":3,"delete_time":0,"type":1}]
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
         * id : 1
         * user_id : 54
         * msg_id : 1
         * send_time : 1575364051
         * status : 1
         * state : 2
         * title : 测试
         * content : 测试内容
         * system : 3
         * delete_time : 0
         * type : 1
         */

        private int id;
        private int user_id;
        private int msg_id;
        private int send_time;
        private int status;
        private int state;
        private String title;
        private String content;
        private int system;
        private int delete_time;
        private int type;

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

        public int getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(int msg_id) {
            this.msg_id = msg_id;
        }

        public int getSend_time() {
            return send_time;
        }

        public void setSend_time(int send_time) {
            this.send_time = send_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getSystem() {
            return system;
        }

        public void setSystem(int system) {
            this.system = system;
        }

        public int getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(int delete_time) {
            this.delete_time = delete_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
