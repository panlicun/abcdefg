package com.plc.abcdefg.kernel.model.common;

public enum ResponseMsgEnum {

        SUCCESS(0,"操作成功"),
        CONNECT_FAIL(101,"连接失败"),
        /**
         * token异常
         */
        TOKEN_EXPIRED(700, "token过期"),
        TOKEN_ERROR(700, "token验证失败"),

        /**
         * 签名异常
         */
        SIGN_ERROR(700, "签名验证失败"),

        PERMISSION_DENIED(411, "权限不足"),

        /**
         * 其他
         */
        AUTH_REQUEST_ERROR(400, "账号密码错误");

        ResponseMsgEnum(int code, String message) {
                this.code = code;
                this.message = message;
        }

        private Integer code;

        private String message;

        public Integer getCode() {
                return code;
        }

        public void setCode(Integer code) {
                this.code = code;
        }

        public String getMessage() {
                return message;
        }

        public void setMessage(String message) {
                this.message = message;
        }
}
