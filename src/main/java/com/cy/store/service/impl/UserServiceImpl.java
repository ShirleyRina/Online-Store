package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.PasswordNotMatchException;
import com.cy.store.service.ex.UserNotFoundException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
//用户模块业务层的实现
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        //通过user参数来获取传过来的username
        String username = user.getUsername();
        //调用Mapper的findByUsername,判断用户名是否被注册过
        User result = userMapper.findByUsername(username);
        //判断结果集是否为null，如果不为null则抛出用户名被占用的异常
        if (result != null) {
            //抛出异常
            throw new UsernameDuplicatedException("用户名被占用");

        }
        //执行注册过程(row == 1)
        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new InsertException("在用户注册过程中产生未知异常");
        }
        //密码加密处理MD5算法
        //串+密码+串 -> MD5算法进行加密，连续加密三次
        //串就是盐值，盐值是一个随机的字符串
        String oldPassword = user.getPassword();
        //获取盐值，随机生成字符串
        String salt = UUID.randomUUID().toString().toUpperCase();
        //将每个用户的盐值记录下来，用来后面登陆的时候来和数据库的加密后的密码做比较
        user.setSalt(salt);
        //再将密码和盐值作为一个整体，加密处理
        String md5Password = getMD5Password(oldPassword,salt);
        //将加密后的密码重新补全设置到User对象中
        user.setPassword(md5Password);


        //补全数据is_delete == 0
        user.setIsDelete(0);
        //补全信息，4个日志信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setEmodifiedTime(date);
    }

    @Override
    public User login(String username, String password) {
        //根据用户名称查询用户是否存在，如果不在，则抛出异常
        User result = userMapper.findByUsername(username);
        if( result == null ){
            throw  new UserNotFoundException("用户数据不存在");
        }
        //检测用户的密码是否匹配
        //1.先获取数据库加密之后的密码
        String oldPassword = result.getPassword();
        //2.和用户输入的密码进行比较
        //2.1先获取盐值salt：上一次注册时候所生成的盐值
        String salt = result.getSalt();
        //2.2将用户输入的密码使用相同的MD5算法进行加密
        String newMd5Password = getMD5Password(password,salt);
        //将密码进行比较
        if (!newMd5Password.equals(oldPassword)){
            throw new PasswordNotMatchException("用户密码错误");
        }
        //判断用户是否已注销： is_delete是否为1
        if (result.getIsDelete() != null && result.getIsDelete() == 1){
            throw new UserNotFoundException("用户数据不存在");
        }
        //调用mapper层的findbyUsername来查询用户的数据,提升了系统的性能，减少数据量的传输
        User user = new User();
        user.setUsername(result.getUsername());
        user.setUid(result.getUid());
        user.setAvatar(result.getAvatar());
        //将当前的用户数据返回,返回的user对象是为了辅助其他页面的展示使用的（uid,username,avartar)
        return user;
    }

    //定义一个MD5算法的加密
    private String getMD5Password(String password,String salt) {
        //MD5加密算法的调用(进行三次加密)
        for (int i = 0; i < 3; i++) {
            DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        //返回加密后的密码
        return password;

    }

}
