package com.ifchange.rpc.position.exception;

/**
 * @ClassName ServiceException
 * @Description: 自定义异常类
 * @Author Yung
 * @Date 2020/1/17
 * @Version V1.0
 **/
public class ServiceException extends RuntimeException {
    private String msg;
    private int code = 500;

    public ServiceException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ServiceException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public ServiceException(int code,String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public ServiceException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
