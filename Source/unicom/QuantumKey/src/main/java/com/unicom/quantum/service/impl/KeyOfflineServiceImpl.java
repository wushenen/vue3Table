package com.unicom.quantum.service.impl;

import com.unicom.quantum.component.Exception.QuantumException;
import com.unicom.quantum.component.ResultHelper;
import com.unicom.quantum.component.annotation.OperateLogAnno;
import com.unicom.quantum.component.util.DataTools;
import com.unicom.quantum.component.util.UtilService;
import com.unicom.quantum.mapper.KeyOfflineMapper;
import com.unicom.quantum.pojo.DTO.KeyOfflineDTO;
import com.unicom.quantum.pojo.KeyOffline;
import com.unicom.quantum.service.KeyOfflineService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeyOfflineServiceImpl implements KeyOfflineService {

    private final String OPERATE_MODEL = "量子密钥模块";

    @Autowired
    private KeyOfflineMapper keyOfflineMapper;

    @Autowired
    private UtilService utilService;

    @Override
    public void addOffKey(int number) throws Exception {
        byte[] keyId = new byte[16];
        byte[] keyValue = new byte[32];
        byte[] random = utilService.generateQuantumRandom(number*48);
        KeyOffline keyOffline = new KeyOffline();
        for (int i = 0; i < number; i++) {
            System.arraycopy(random,i*48,keyId,0,16);
            System.arraycopy(random,i*48+16,keyValue,0,32);
            keyOffline.setKeyId(keyId);
            keyOffline.setKeyValue(DataTools.encryptMessage(keyValue));
            keyOfflineMapper.addOffKey(keyOffline);
        }
    }

    @OperateLogAnno(operateDesc = "量子密钥充注", operateModel = OPERATE_MODEL)
    @Override
    public List<KeyOfflineDTO> getOffKey(Long start, Long end) throws Exception {
        if (end - start > 2000)
            throw new QuantumException(ResultHelper.genResult(1,"充注密钥数不得大于2000"));
        if (end < start)
            throw new QuantumException(ResultHelper.genResult(1,"充注结束值不得小于开始值"));
        ArrayList<KeyOfflineDTO> list = new ArrayList<>();
        Long offlineKeyNum = keyOfflineMapper.countOfflineKeyNum();
        int sub = end.intValue() - offlineKeyNum.intValue();
        if (sub > 0){
            addOffKey(sub);
        }
        List<KeyOffline> offKeys = keyOfflineMapper.getOffKey(start, end);
        for (KeyOffline offKey : offKeys) {
            KeyOfflineDTO dto = new KeyOfflineDTO();
            dto.setKeyId(Base64.encodeBase64String(offKey.getKeyId()));
            dto.setKeyValue(Base64.encodeBase64String(DataTools.decryptMessage(offKey.getKeyValue())));
            list.add(dto);
        }
        return list;
    }
}
