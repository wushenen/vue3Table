package com.unicom.quantum.service.impl;

import com.unicom.quantum.component.annotation.OperateLogAnno;
import com.unicom.quantum.mapper.GroupDeviceUserMapper;
import com.unicom.quantum.pojo.DTO.GroupDeviceUserInfo;
import com.unicom.quantum.service.GroupDeviceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupDeviceUserServiceImpl implements GroupDeviceUserService {

    private final String OPERATE_MODEL = "分组模块";

    @Autowired
    private GroupDeviceUserMapper groupDeviceUserMapper;

    @OperateLogAnno(operateDesc = "添加分组终端用户", operateModel = OPERATE_MODEL)
    @Override
    public int addGroupDeviceUser(List<Integer> deviceIds, int groupId) {
        for (Integer deviceId : deviceIds) {
            if (!groupDeviceUserMapper.existAddGroupDeviceUser(deviceId,groupId))
                groupDeviceUserMapper.addGroupDeviceUser(deviceId,groupId);
        }
        return 0;
    }

    @OperateLogAnno(operateDesc = "查看分组终端用户", operateModel = OPERATE_MODEL)
    @Override
    public List<GroupDeviceUserInfo> groupDeviceUserList(int groupId) {
        return groupDeviceUserMapper.groupDeviceUserList(groupId);
    }

    @OperateLogAnno(operateDesc = "删除分组终端用户", operateModel = OPERATE_MODEL)
    @Override
    public int deleteGroupDeviceUser(List<Integer> ids) {
        return groupDeviceUserMapper.deleteGroupDeviceUser(ids);
    }
}
