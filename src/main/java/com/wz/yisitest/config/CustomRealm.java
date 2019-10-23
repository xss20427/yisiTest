package com.wz.yisitest.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wz.yisitest.entity.Permission;
import com.wz.yisitest.entity.Role;
import com.wz.yisitest.entity.User;
import com.wz.yisitest.service.impl.RoleServiceImpl;
import com.wz.yisitest.service.impl.UserServiceImpl;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @author：yisi
 * @date：17/10/2019 --------------
 */
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("-------权限认证方法--------");
        //获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        //根据用户名去数据库查询用户信息
        User user = new User();
        user.setRoleList(userService.findUserRole(name));//查找对应的所有角色 这里只有一个角色
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoleList()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getName());
            role.setPermissionList(roleService.findRolePermission(role.getId())); //查找权限
            //添加权限
            for (Permission permissions : role.getPermissionList()) {
                simpleAuthorizationInfo.addStringPermission(permissions.getName());//赋予权限
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("-------身份认证方法--------");
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUser, name), false);
        if (null == user) {
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword(), getName());
            //更新最近登录时间
            user.setLastLoginTime(LocalDateTime.now());
            userService.saveOrUpdate(user);
            return simpleAuthenticationInfo;
        }
    }
}
