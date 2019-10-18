package com.wz.yisitest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wz.yisitest.dao.RoleMapper;
import com.wz.yisitest.entity.Permission;
import com.wz.yisitest.entity.Role;
import com.wz.yisitest.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author yisi
 * @since 2019-10-17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<Permission> findRolePermission(Long id) {
        return baseMapper.findRolePermission(id);
    }
}
