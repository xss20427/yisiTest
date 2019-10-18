package com.wz.yisitest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wz.yisitest.entity.Permission;
import com.wz.yisitest.entity.Role;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author yisi
 * @since 2019-10-17
 */
public interface RoleService extends IService<Role> {
    List<Permission> findRolePermission(Long id);
}
