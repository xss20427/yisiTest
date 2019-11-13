package com.wz.yisitest.config;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wz.yisitest.entity.Permission;
import com.wz.yisitest.entity.Role;
import com.wz.yisitest.entity.User;
import com.wz.yisitest.service.impl.RoleServiceImpl;
import com.wz.yisitest.service.impl.UserServiceImpl;
import com.wz.yisitest.util.JWTUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
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
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTtoken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("-------权限认证方法--------");
        //获取登录用户名
        String name = JWTUtil.getUserName(principalCollection.toString());
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
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        System.out.println("-------身份认证方法--------");
        String token = (String) authenticationToken.getCredentials();
        String userName = JWTUtil.getUserName(token);
        if (null == userName) {
            throw new AuthenticationException("token invalid");
        }
        User user = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUser, userName), false);
        if (null == user) {
            throw new AuthenticationException("User didn't existed!");
        }
        if (!JWTUtil.verify(token, userName, user.getPassword())) {
            System.out.println("faild");
            throw new AuthenticationException("Username or password error");
        }
        //更新最近登录时间
        user.setLastLoginTime(LocalDateTime.now());
        userService.saveOrUpdate(user);
        return new SimpleAuthenticationInfo(token, token, "relam");
    }
}
