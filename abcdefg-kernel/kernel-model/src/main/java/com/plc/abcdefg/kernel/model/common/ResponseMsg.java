package com.plc.abcdefg.kernel.model.common;

public class ResponseMsg {
    private int code = 0;
    private String message;
    private Object data;
    private Integer count = null;

    public ResponseMsg() {
        super();
    }
    public ResponseMsg(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseMsg(Integer code, String message, Object data, Integer count) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.count = count;
    }

    public ResponseMsg(ResponseMsgEnum responseMsgEnum, Object data) {
        this.code = responseMsgEnum.getCode();
        this.message = responseMsgEnum.getMessage();
        this.data = data;
    }

    public ResponseMsg(ResponseMsgEnum responseMsgEnum, Object data, Integer count) {
        this.code = responseMsgEnum.getCode();
        this.message = responseMsgEnum.getMessage();
        this.data = data;
        this.count = count;
    }
    public ResponseMsg(ResponseMsgEnum responseMsgEnum) {
        this.code = responseMsgEnum.getCode();
        this.message = responseMsgEnum.getMessage();
    }

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
