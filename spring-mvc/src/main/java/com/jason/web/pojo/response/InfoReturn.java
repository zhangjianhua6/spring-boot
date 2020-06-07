package com.jason.web.pojo.response;

/**
 * @author: zhangjianhua
 * @date: 2019-12-07 15:47
 **/
public class InfoReturn<T> extends AopResponse {

    private static final long serialVersionUID = 8716459533915780739L;

    private T data;

    public InfoReturn(){
    }

    public InfoReturn(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
