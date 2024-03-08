package com.cy.store.mapper;

import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest //标注当前类是一个测试类，测试类不会被一起打包
@RunWith(SpringRunner.class)//RunWith表示启动这个单元测试类(单元测试类是不能运行的）需要传入一个参数，必须是SpringRunner的实例类型


public class UserMapperTests {
    //IDEA有检测功能，interface不能直接创建bean(动态代理计数)
    @Autowired
    private UserMapper userMapper;
    //单元测试的方法1，必须被@test修饰2.返回类型必须是void 3.方法的参数列表不能指定任何类型 4，方法的访问修饰符必须是public
    @Test
    public void insert(){
        User user = new User();
        user.setUsername("tim");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);

    }
    @Test
    public void findByUsername(){
        User user = userMapper.findByUsername("time");
        System.out.println(user);

    }
}
