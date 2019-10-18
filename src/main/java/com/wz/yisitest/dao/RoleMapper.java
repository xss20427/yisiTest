package com.wz.yisitest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wz.yisitest.entity.Permission;
import com.wz.yisitest.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author yisi
 * @since 2019-10-17
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<Permission> findRolePermission(Long id);
}
