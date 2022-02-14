package com.unicom.quantum.service.impl;

import com.unicom.quantum.component.Exception.QuantumException;
import com.unicom.quantum.component.ResultHelper;
import com.unicom.quantum.component.util.UtilService;
import com.unicom.quantum.mapper.DeviceUserMapper;
import com.unicom.quantum.service.DeviceUserService;
import com.unicom.quantum.pojo.DTO.ExportDeviceUserInfo;
import com.unicom.quantum.pojo.DeviceUser;
import com.unicom.quantum.component.annotation.OperateLogAnno;
import com.unicom.quantum.component.util.HexUtils;
import com.unicom.quantum.controller.vo.UpdateUserInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceUserServiceImpl implements DeviceUserService {

    private final String OPERATE_MODEL = "终端用户模块";

    @Autowired
    private DeviceUserMapper deviceUserMapper;

    @Autowired
    private UtilService utilService;

    @OperateLogAnno(operateDesc = "查看所有终端用户信息", operateModel = OPERATE_MODEL)
    @Override
    public List<DeviceUser> listAllDeviceUser(String deviceName) {
        List<DeviceUser> deviceUsers = deviceUserMapper.listAllDeviceUser(deviceName);
        for (DeviceUser deviceUser : deviceUsers) {
            deviceUser.setPassword("***");
            deviceUser.setEncKey("***".getBytes());
        }
        return deviceUsers;
    }

    @OperateLogAnno(operateDesc = "查看特定终端用户信息", operateModel = OPERATE_MODEL)
    @Override
    public DeviceUser getDeviceInfo(int deviceId) {
        DeviceUser deviceInfo = deviceUserMapper.getDeviceInfo(deviceId);
        deviceInfo.setPassword(utilService.decryptCBCWithPadding(deviceInfo.getPassword(),UtilService.SM4KEY));
        deviceInfo.setEncKey(utilService.decryptCBCWithPadding(deviceInfo.getEncKey(),UtilService.SM4KEY));
        return deviceInfo;
    }

    @Override
    public boolean userNameIsExist(String deviceName) {
        return deviceUserMapper.userNameIsExist(deviceName);
    }

    @OperateLogAnno(operateDesc = "添加终端用户信息", operateModel = OPERATE_MODEL)
    @Override
    public int addDeviceUser(DeviceUser deviceUser) throws Exception {
        if (deviceUserMapper.userNameIsExist(deviceUser.getDeviceName())) {
            throw new QuantumException(ResultHelper.genResult(1,"用户名已被占用"));
        }
        deviceUser.setPassword(utilService.encryptCBCWithPadding(deviceUser.getPassword(),UtilService.SM4KEY));
        if (deviceUser.getEncKey() != null) {
            deviceUser.setEncKey(utilService.encryptCBCWithPadding(deviceUser.getEncKey(),UtilService.SM4KEY));
        }
        if (deviceUser.getUserType() == 1){
            deviceUser.setEncKey(utilService.generateQuantumRandom(32));
        }
        deviceUserMapper.addDeviceUser(deviceUser);
        return 0;
    }

    @OperateLogAnno(operateDesc = "修改终端用户信息", operateModel = OPERATE_MODEL)
    @Override
    public int updateDevice(UpdateUserInfoRequest updateUserInfoRequest) throws QuantumException {
        String oldDeviceName = deviceUserMapper.getDeviceInfo(updateUserInfoRequest.getDeviceId()).getDeviceName();
        if (!oldDeviceName.equals(updateUserInfoRequest.getDeviceName()) && deviceUserMapper.userNameIsExist(updateUserInfoRequest.getDeviceName())) {
            throw new QuantumException(ResultHelper.genResult(1,"用户名已被占用"));
        }
        if (updateUserInfoRequest.getPassword()!=null) {
            updateUserInfoRequest.setPassword(utilService.encryptCBCWithPadding(updateUserInfoRequest.getPassword(),UtilService.SM4KEY));
        }
        return deviceUserMapper.updateDevice(updateUserInfoRequest);
    }

    @OperateLogAnno(operateDesc = "删除特定终端用户信息", operateModel = OPERATE_MODEL)
    @Override
    public int deleteDevice(int deviceId) {
        deviceUserMapper.deleteDeviceAuthByDeviceId(deviceId);
        deviceUserMapper.deleteGroupUserByDeviceId(deviceId);
        DeviceUser deviceInfo = deviceUserMapper.getDeviceInfo(deviceId);
        deviceUserMapper.deleteKeyInfoByDeviceName(deviceInfo.getDeviceName());
        deviceUserMapper.deleteKeyLimitByDeviceName(deviceInfo.getDeviceName());
        deviceUserMapper.deleteAppDevice(deviceId);
        deviceUserMapper.deleteDeviceStatus(deviceInfo.getDeviceName());
        deviceUserMapper.deleteDevice(deviceId);
        return 0;
    }

    @OperateLogAnno(operateDesc = "查看终端用户信息", operateModel = OPERATE_MODEL)
    @Override
    public List<DeviceUser> queryDeviceUser(String deviceName) {
        return deviceUserMapper.queryDeviceUser(deviceName);
    }

    @Override
    public List<ExportDeviceUserInfo> exportDeviceUserInfo(List<Integer> deviceIds) {
        List<ExportDeviceUserInfo> userInfos = deviceUserMapper.exportDeviceUserInfo(deviceIds);
        for (ExportDeviceUserInfo userInfo : userInfos) {
            userInfo.setPassword(utilService.decryptCBCWithPadding(userInfo.getPassword(),UtilService.SM4KEY));
            if (userInfo.getEncKey() != null) {
                userInfo.setEncKey(HexUtils.bytesToHexString(utilService.decryptCBCWithPadding(HexUtils.hexStringToBytes(userInfo.getEncKey()),UtilService.SM4KEY)));
            }
        }
        return userInfos;
    }

    @Override
    public String getEncKey(String deviceName) {
        return HexUtils.bytesToHexString(utilService.decryptCBCWithPadding(HexUtils.hexStringToBytes(deviceUserMapper.getEncKey(deviceName)),UtilService.SM4KEY));
//        return utilService.decryptCBC(deviceUserMapper.getEncKey(deviceName),UtilService.SM4KEY);
    }
}