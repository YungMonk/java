package com.ifchange.rpc.position.json;

/**
 * @ClassName ApiResponse
 * @Description: 响应结果
 * @Author Yung
 * @Date 2020/1/17
 * @Version V1.0
 **/
public class ApiResponse<T> {
    private int err_no = 0;
    private String err_msg = "";
    private T results;

    public ApiResponse() {
    }

    public ApiResponse(T results) {
        this.results = results;
    }

    public ApiResponse(String err_msg, T results) {
        this.err_no = -1;
        this.err_msg = err_msg;
        this.results = results;
    }

    public ApiResponse(int err_no, String err_msg, T results) {
        this.err_no = err_no;
        this.err_msg = err_msg;
        this.results = results;
    }

    public int getErr_no() {
        return this.err_no;
    }

    public String getErr_msg() {
        return this.err_msg;
    }

    public T getResults() {
        return this.results;
    }
}
