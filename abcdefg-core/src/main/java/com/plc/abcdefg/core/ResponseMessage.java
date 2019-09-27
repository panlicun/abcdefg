package com.plc.abcdefg.core;


import com.plc.abcdefg.core.exception.ServiceExceptionEnum;

/**
 * Created by Administrator on 2018/2/28 0028.
 */
public class ResponseMessage {
    private Integer code;
    private String message;
    private Object data;
    private Integer count;

    public ResponseMessage() {
        super();
    }
    public ResponseMessage(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseMessage(Integer code, String message, Object data, Integer count) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.count = count;
    }

    public ResponseMessage(ServiceExceptionEnum serviceExceptionEnum, Object data) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
        this.data = data;
    }

    public ResponseMessage(ServiceExceptionEnum serviceExceptionEnum, Object data, Integer count) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
        this.data = data;
        this.count = count;
    }
    public ResponseMessage(ServiceExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
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
