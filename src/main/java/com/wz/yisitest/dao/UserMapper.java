package com.wz.yisitest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wz.yisitest.entity.Role;
import com.wz.yisitest.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author yisi
 * @since 2019-10-17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<Role> findUserRole(String name);

    void registerUser(long user_id, long role_id);

    void registerPermi(long Perm_id, long role_id);

}
