package com.cucc.unicom.shiro;

import com.cucc.unicom.component.util.JWTUtil;
import com.cucc.unicom.pojo.ApiResource;
import com.cucc.unicom.pojo.DTO.GroupAuthInfo;
import com.cucc.unicom.pojo.DTO.RoleAuthInfo;
import com.cucc.unicom.pojo.DeviceUser;
import com.cucc.unicom.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ShiroAuthService shiroAuthService;

    @Autowired
    private RoleAuthService roleAuthService;
    @Autowired
    private GroupAuthService groupAuthService;

    @Autowired
    private ApiResourceService apiResourceService;
    /**
     * 权限校验
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        DeviceUser deviceUser = (DeviceUser) SecurityUtils.getSubject().getPrincipal();
        List<Integer> roleIds = null;
        List<Integer> groupIds = null;
        List<RoleAuthInfo> roleAuth = null;
        List<GroupAuthInfo> groupAuth = null;
        HashSet<Integer> apiIdSet = new HashSet<>();
        info.addRole("deviceUser");
        int deviceId = deviceUser.getDeviceId();
        roleIds = shiroAuthService.getRoleInfosByDeviceId(deviceId);
        groupIds = shiroAuthService.getGroupInfosByDeviceId(deviceId);
        List<Integer> strategyIdByDeviceId = shiroAuthService.getStrategyIdByDeviceId(deviceId);
        if (strategyIdByDeviceId != null && !strategyIdByDeviceId.isEmpty()){
            for (Integer strategyId : strategyIdByDeviceId) {
                List<Integer> apiId = shiroAuthService.getApiIdByStrategyId(strategyId);
                apiIdSet.addAll(apiId);
            }
        }
        if (roleIds != null && !roleIds.isEmpty()){
            for (Integer roleId : roleIds) {
                roleAuth = roleAuthService.getRoleAuth(roleId);
                List<Integer> strategyIds = shiroAuthService.getStrategyIdByRoleId(roleId);
                for (RoleAuthInfo roleAuthInfo : roleAuth) {
                    apiIdSet.add(roleAuthInfo.getApiId());
                }
                for (Integer strategyId : strategyIds) {
                    List<Integer> apiId = shiroAuthService.getApiIdByStrategyId(strategyId);
                    apiIdSet.addAll(apiId);
                }
            }
        }
        if (groupIds != null && !groupIds.isEmpty()){
            for (Integer groupId : groupIds) {
                groupAuth = groupAuthService.getGroupAuth(groupId);
                List<Integer> strategyIds = shiroAuthService.getStrategyIdByGroupId(groupId);
                for (GroupAuthInfo groupAuthInfo : groupAuth) {
                    apiIdSet.add(groupAuthInfo.getApiId());
                }
                for (Integer strategyId : strategyIds) {
                    List<Integer> apiId = shiroAuthService.getApiIdByStrategyId(strategyId);
                    apiIdSet.addAll(apiId);
                }
            }
        }
        if (apiIdSet.size() > 0){
            for (Integer apiId : apiIdSet) {
                ApiResource resourceInfo = apiResourceService.getResourceInfo(apiId);
                info.addStringPermission(resourceInfo.getApiURL());
            }
        }
        return info;
    }

    /**
     * 身份认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userNamePasswordToken = (UsernamePasswordToken) token;
        String userToken = userNamePasswordToken.getUsername();
        String username = null;
        DeviceUser deviceUser = null;
        try {
            username = JWTUtil.getUsername(userNamePasswordToken.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            throw  new AuthenticationException("用户不存在");
        }
        try {
            deviceUser = loginService.deviceUserLogin(username);
        } catch (Exception e) {
            throw new AuthenticationException("用户不存在！");
        }
        if (deviceUser == null || !JWTUtil.verifyToken(userToken, username)) {
            throw new AuthenticationException("token认证失败！");
        }
        return new SimpleAuthenticationInfo(deviceUser,userToken, getName());
    }
}
