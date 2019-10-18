package com.wz.yisitest.service;

import com.wz.yisitest.entity.Role;
import com.wz.yisitest.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author yisi
 * @since 2019-10-17
 */
public interface UserService extends IService<User> {
    List<Role> findUserRole(String name);
    int registerUser(User user);
}
