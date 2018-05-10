package com.itgowo.gamestzb.Entity;

public class BaseResponse<Data> {
    private Integer code;
    private String msg;
    private Data data;

    public Integer getCode() {
        return code;
    }

    public BaseResponse setCode(Integer code) {
        this.code = code;
        return this;
    }
    public boolean isSuccess(){
        return getCode()==1;
    }
    public String getMsg() {
        return msg;
    }

    public BaseResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Data getData() {
        return data;
    }

    public BaseResponse<Data> setData(Data data) {
        this.data = data;
        return this;
    }
}
