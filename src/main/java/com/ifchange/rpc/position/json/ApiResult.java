package com.ifchange.rpc.position.json;

/**
 * @ClassName Response
 * @Description: 返回的json对象
 * @Author Yung
 * @Date 2020/1/17
 * @Version V1.0
 **/
public class ApiResult {
    private ApiHeader header;
    private ApiResponse<?> response;

    public ApiResult(){
        this.header = new ApiHeader();
    }

    public ApiHeader setHeader(ApiHeader header) {
        return this.header = header;
    }

    public ApiHeader getHeader() {
        return header;
    }

    public ApiResponse<?> getResponse() {
        return response;
    }

    public ApiResult success() {
        this.response = new ApiResponse<String>();
        return this;
    }

    public ApiResult success(String result) {
        this.response = new ApiResponse<String>(result);
        return this;
    }

    public ApiResult success(Object result) {
        this.response = new ApiResponse<Object>(result);
        return this;
    }

    public ApiResult failure() {
        this.response = new ApiResponse<String>("", "");
        return this;
    }

    public ApiResult failure(String message) {
        this.response = new ApiResponse<String>(message, "");
        return this;
    }

    public ApiResult failure(int code, String message) {
        this.response = new ApiResponse<String>(code, message, "");
        return this;
    }
}
