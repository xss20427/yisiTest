package com.wz.yisitest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wz.yisitest.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author yisi
 * @since 2019-10-17
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

}
