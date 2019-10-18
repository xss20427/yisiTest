package com.wz.yisitest.service.impl;

import com.wz.yisitest.entity.Permission;
import com.wz.yisitest.dao.PermissionMapper;
import com.wz.yisitest.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author yisi
 * @since 2019-10-17
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
