package com.wz.yisitest.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wz.yisitest.dao.UserMapper;
import com.wz.yisitest.entity.Role;
import com.wz.yisitest.entity.User;
import com.wz.yisitest.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author yisi
 * @since 2019-10-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public List<Role> findUserRole(String name) {
        return userMapper.findUserRole(name);
    }

    @Override
    public int registerUser(User user) {
        if (user.getUser() != null) {
            if (userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUser, user.getUser())) != null) {
                return -1;
            } else {
                userMapper.insert(user);//注册成功
                userMapper.registerUser(user.getId(), 2);//添加角色 roleid 1:admin 2:普通 默认注册为普通用户
                userMapper.registerPermi(2, 2);//添加权限 普通用户 user:*
            }
        }
        return 1;
    }
}
