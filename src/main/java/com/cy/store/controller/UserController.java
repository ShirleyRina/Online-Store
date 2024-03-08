package com.cy.store.controller;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
//import com.cy.store.service.ex.InsertException;
//import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JasonResult;
//import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController

@RequestMapping("users")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;
    //约定大于配置，省略掉大量配置甚至注解的编写

    //接受数据的方式：请求处理的方法的参数列表设置为pojo类型来接受前端的数据，springboot会将前端URL地址中的参数和pojo类的
    //属性名进行比较，如果两个名称相同则将值注入到pojo类中对应的属性上。
    @RequestMapping("reg")
    //@ResponseBody//此方法的响应结果以Jason的格式进行响应，给到前端
    public JasonResult<Void> reg(User user) {
        userService.reg(user);
        return new JasonResult<>(OK);

    }
    //接受数据的方式：请求处理方法的参数列表设置为非pojo类型
    //springboot会直接将请求的参数名和方法的参数名直接进行比较，如果名称相同，则完成值的依赖注入
    @RequestMapping("login")
    public JasonResult<User> login(String username, String password, HttpSession session){
        User data = userService.login(username,password);
        //向session中的对象完成数据的绑定（session全局的）
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());
        //获取绑定的数据
        System.out.println(getuidFromSession(session));
        System.out.println(getUsernameFromSession(session));
        return new JasonResult<User>(OK,data);
    }
}
    /*

    @RequestMapping("reg")
    //@ResponseBody//此方法的响应结果以Jason的格式进行响应，给到前端
    public JasonResult<Void> reg(User user){
        //创建响应结果的对象
        JasonResult<Void> result = new JasonResult<>();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("用户注册成功");
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        }
        catch (InsertException e) {
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }
        return result;

    }
     */

