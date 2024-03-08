package com.cy.store.mapper;
import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Mapper;

/*用户模块的接口*/
@Mapper
public interface UserMapper {
    /**
     * 插入用户的数据
     * @param user 用户的数据
     * @return 受影响的行数（增删改都有受影响的行数都有返回值，根据返回值判断是否执行语句
     */

    Integer insert(User user);

    /**
     * 根据用户名来查询用户的数据
     * @param username 用户名
     * @return 如果找到了就返回用户的数据，如果没有即返回null值
     */
    User findByUsername(String username);
}
