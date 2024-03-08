package com.cy.store.util;

import java.io.Serializable;

public class JasonResult<E> implements Serializable {
    //状态码
    private Integer state;
    //描述信息
    private String message;
    //数据，不确定类型设定为泛型
    private E  data;

    public JasonResult(){

    }

    public JasonResult(Integer state) {
        this.state = state;
    }

    public JasonResult(Throwable e) {
        this.message = e.getMessage();
    }


    public JasonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
