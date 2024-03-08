package com.cy.store.controller;


import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.PasswordNotMatchException;
import com.cy.store.service.ex.ServiceException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JasonResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.attribute.UserPrincipalNotFoundException;

//表示控制层类的基类，主要用来做异常的捕获和处理
public class BaseController {
    //表示操作成功的码
    public static final int OK = 200;

    //请求处理方法，这个方法的返回值需要传递给前端的数据
    //自动将异常的对象传递给此方法上
    //当项目中产生了异常，会被统一拦截到此方法中，此方法此时就是请求处理方法，方法的返回值直接给到前端
    @ExceptionHandler(ServiceException.class)//用来统一处理抛出的异常
    public JasonResult<Void> handleException(Throwable e){
        JasonResult<Void> result = new JasonResult<>(e);
        if (e instanceof UsernameDuplicatedException){
            result.setState(4000);
            result.setMessage("用户名已被占用");
        } else if(e instanceof UserPrincipalNotFoundException){
            result.setState(5001);
            result.setMessage("用户数据不存在的异常");
        } else if(e instanceof PasswordNotMatchException){
            result.setState(5002);
            result.setMessage("用户名的密码错误的异常");
        }else if(e instanceof InsertException){
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }
        return result;

    }

    /**
     * 获取session对象中的uid
     * @param session session对象
     * @return 当前登录的用户uid的值
     */
    protected final Integer getuidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());


    }

    /**
     * 获取当前登陆用户的username
     * @param session session对象
     * @return 当前登录用户的用户名
     */
    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();

    }




}
