package com.unicom.quantum.controller;

import com.alibaba.fastjson.JSONObject;
import com.unicom.quantum.component.Result;
import com.unicom.quantum.component.ResultHelper;
import com.unicom.quantum.component.util.JWTUtil;
import com.unicom.quantum.component.util.SM3Util;
import com.unicom.quantum.component.util.UtilService;
import com.unicom.quantum.controller.vo.AppLoginRequest;
import com.unicom.quantum.controller.vo.DeviceUserLoginRequest;
import com.unicom.quantum.pojo.App;
import com.unicom.quantum.pojo.DeviceUser;
import com.unicom.quantum.service.LoginService;
import com.unicom.quantum.shiro.MyUserNamePasswordToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;


@Api(tags = {"登录接口"})
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private SessionDAO sessionDAO;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UtilService utilService;


    @ApiOperation(value = "终端用户登录",notes = "终端用户登录")
    @RequestMapping(value = "/deviceLogin",method = RequestMethod.POST)
    @ResponseBody
    public Result unicomDeviceLogin(@RequestBody DeviceUserLoginRequest deviceUserLoginRequest) {
        boolean tag = false;
        String deviceName = StringUtils.newStringUtf8(Base64.decodeBase64(deviceUserLoginRequest.getDeviceName()));
        byte[] hmac1 = Base64.decodeBase64(deviceUserLoginRequest.getPassword());
        DeviceUser deviceUser = loginService.deviceUserLogin(deviceName);
        if (deviceUser !=null){
            if (deviceUser.getUserType() ==1) {
                byte[] hash = SM3Util.hash(utilService.decryptCBCWithPadding(deviceUser.getPassword(), UtilService.SM4KEY).getBytes());
                byte[] hmac = SM3Util.hmac(utilService.decryptCBCWithPadding(deviceUser.getEncKey(), UtilService.SM4KEY), hash);
                tag = Arrays.equals(hmac, hmac1);
            }
            if (deviceUser.getUserType() == 0)
                tag = utilService.decryptCBCWithPadding(deviceUser.getPassword(),UtilService.SM4KEY).equals(deviceUserLoginRequest.getPassword());
            //判断用户成功后，获取用户的权限信息
            if (tag){
                String token = JWTUtil.generateTokenWithId(deviceUser.getDeviceId(),deviceUser.getDeviceName());
                MyUserNamePasswordToken userToken = new MyUserNamePasswordToken(token, token,"deviceUser");
                Subject subject = SecurityUtils.getSubject();
                try {
                    subject.logout();
                    subject.login(userToken);
                    Collection<Session> sessions = sessionDAO.getActiveSessions();
                    for (Session session : sessions) {
                        if (deviceName.equals(session.getAttribute("loginedUser"))) {
                            session.setTimeout(0);
                        }
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("token", token);
                    subject.getSession().setAttribute("loginedUser", deviceName);
                    return ResultHelper.genResultWithSuccess(jsonObject);
                } catch (UnknownAccountException e){
                    return ResultHelper.genResult(1,"用户名不存在");
                } catch (IncorrectCredentialsException e) {
                    return ResultHelper.genResult(1, "密码错误");
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResultHelper.genResult(1, e.getMessage());
                }
            }else
                return ResultHelper.genResult(1,"用户名或密码错误");
        }else
            return ResultHelper.genResult(1,"用户不存在");
    }

    @ApiOperation(value = "应用登录",notes = "应用登录")
    @RequestMapping(value = "/appLogin",method = RequestMethod.POST)
    @ResponseBody
    public Result unicomAppLogin(@RequestBody AppLoginRequest appLoginRequest) {
        String appKey = StringUtils.newStringUtf8(Base64.decodeBase64(appLoginRequest.getAppKey()));
        App appLogin = loginService.appLogin(utilService.decryptCBCWithPadding(appKey,UtilService.SM4KEY));
        if (appLogin !=null){
            String appSecret = utilService.decryptCBCWithPadding(appLogin.getAppSecret(), UtilService.SM4KEY);
            if (appSecret.equals(StringUtils.newStringUtf8(Base64.decodeBase64(appLoginRequest.getAppKey())))) {
                String token = JWTUtil.generateToken(appLogin.getAppName());
                UsernamePasswordToken userToken = new MyUserNamePasswordToken(token, token,"deviceUser");
                Subject subject = SecurityUtils.getSubject();
                try {
                    subject.logout();
                    subject.login(userToken);
                    Collection<Session> sessions = sessionDAO.getActiveSessions();
                    for (Session session : sessions) {
                        if (appLogin.getAppName().equals(session.getAttribute("loginedUser"))) {
                            session.setTimeout(0);
                        }
                    }
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("token", token);
                    subject.getSession().setAttribute("loginedUser", appLogin.getAppName());
                    return ResultHelper.genResultWithSuccess(jsonObject);
                } catch (UnknownAccountException e){
                    return ResultHelper.genResult(1,"用户名不存在");
                } catch (IncorrectCredentialsException e) {
                    return ResultHelper.genResult(1, "密码错误");
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResultHelper.genResult(1, e.getMessage());
                }
            }else
                return ResultHelper.genResult(1,"用户名或密码错误");
        }else
            return ResultHelper.genResult(1,"用户不存在");
    }


    @ApiOperation(value = "注销",notes = "用户注销登录")
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Result unicomLogout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null){
            subject.logout();
            SecurityUtils.getSecurityManager().logout(subject);
        }
        return ResultHelper.genResultWithSuccess();
    }

    @ApiOperation(value = "无权限",notes = "无权限")
    @ResponseBody
    @RequestMapping(value = "/noPerm", method = RequestMethod.GET)
    public Result unicomNoPerm() {
        return ResultHelper.genResult(403,"no perms");
    }


}