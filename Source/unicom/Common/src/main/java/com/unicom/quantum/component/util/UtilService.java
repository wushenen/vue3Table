package com.unicom.quantum.component.util;

import com.alibaba.fastjson.JSONObject;
import com.unicom.quantum.jni.KMSJNI;
import com.unicom.quantum.jni.QR902JNI;
import com.unicom.quantum.mapper.KeySourceConfigMapper;
import com.unicom.quantum.pojo.dto.KeySourceDetail;
import com.unicom.quantum.service.KeySourceInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class UtilService {
    private static final Logger logger = LoggerFactory.getLogger(UtilService.class);

    private final static int BUFFER_LENGTH = 51200;
    private static byte[] QRNG_BUFFER = new byte[BUFFER_LENGTH];
    private static byte[] QRNG_BUFFER2 = new byte[BUFFER_LENGTH];
    public static volatile int QRNG_INDEX = 0;
    public static volatile int CURRENT = 1;
    public static volatile boolean FIRST_COME = false;

    static {
        try {
            QRNG_BUFFER = QrngUtil.genrateRandom(BUFFER_LENGTH);
            QRNG_BUFFER2 = QrngUtil.genrateRandom(BUFFER_LENGTH);
            logger.info("----------QR301/302初始化完毕---------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private KeySourceConfigMapper keySourceConfigMapper;
    @Autowired
    private KeySourceInfoService keySourceInfoService;

    private final String SM4IV = "00000000000000000000000000000000";
    public static final String SM4KEY = "304C1673505BF98B894DC2C496D24B33";

    public byte[] generateQuantumRandom(int length) throws Exception {
        List<KeySourceDetail> keySourceConfigs = keySourceConfigMapper.getKeySourceConfigs();
        int keySource = 0;
        byte[] random = new byte[length];
        byte[] random2 = new byte[length];
        if (keySourceConfigs.size() != 0) {
            for (int i = 0; i < keySourceConfigs.size(); i++) {
                long startTime = System.currentTimeMillis();
                if(keySourceConfigs.get(i).getPriority() != 4){
                    logger.info("随机数获取源：{}",keySourceConfigs.get(i).getKeySource());
                    keySource = keySourceConfigs.get(i).getKeySource();
                    switch (keySourceConfigs.get(i).getKeySource()) {
                        case 1://qrng
                            logger.info("QRNG获取量子随机数");
                            switch (CURRENT){
                                case 1:
                                    try {
                                        System.arraycopy(QRNG_BUFFER, QRNG_INDEX, random, 0, length);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 2:
                                    try {
                                        System.arraycopy(QRNG_BUFFER2, QRNG_INDEX, random, 0, length);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                default:
                                    break;
                            }
                            QRNG_INDEX = QRNG_INDEX + length;
                            if (QRNG_INDEX > 50000) {
                                QRNG_INDEX = 0;
                                FIRST_COME = true;
                            }
                            CompletableFuture.runAsync(()-> {
                                while (FIRST_COME) {
                                    switch (CURRENT) {
                                        case 1:
                                            CURRENT = 2;
                                            FIRST_COME = false;
                                            CompletableFuture.runAsync(()->{
                                                try {
                                                    System.arraycopy(QrngUtil.genrateRandom(BUFFER_LENGTH), 0, QRNG_BUFFER, 0, BUFFER_LENGTH);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            });
                                            break;
                                        case 2:
                                            CURRENT = 1;
                                            FIRST_COME = false;
                                            CompletableFuture.runAsync(()->{
                                                try {
                                                    System.arraycopy(QrngUtil.genrateRandom(BUFFER_LENGTH), 0, QRNG_BUFFER2, 0, BUFFER_LENGTH);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            });
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            });
                            break;
                        case 2://902
                            logger.info("高速QRNG获取量子随机数");
                            QR902JNI qr902JNI = new QR902JNI();
                            String sourceIp = keySourceConfigs.get(i).getSourceIp();
                            String sourceIp2 = keySourceConfigs.get(i).getSourceIp2();
                            if (sourceIp == null)
                                break;
                            if (length < 8192) {
                                byte[] bytes = qr902JNI.QRNG_read_random(sourceIp, 8192);
                                if (bytes != null) {
                                    System.arraycopy(bytes, 0, random, 0, length);
                                }else {
                                    if (sourceIp2 != null){
                                        byte[] bytes1 = qr902JNI.QRNG_read_random(sourceIp2, 8192);
                                        if (bytes1 != null) {
                                            System.arraycopy(bytes1, 0, random, 0, length);
                                        }
                                    }
                                }
                            } else {
                                int num = length/8192 ;
                                if (length%8192 != 0) {
                                    num += 1;
                                }
                                byte[] bytes = qr902JNI.QRNG_read_random(sourceIp, num*8192);
                                if (bytes != null) {
                                    System.arraycopy(bytes, 0, random, 0, length);
                                }else{
                                    if (sourceIp2 != null) {
                                        byte[] bytes1 = qr902JNI.QRNG_read_random(sourceIp2, num * 8192);
                                        if (bytes1 != null)
                                            System.arraycopy(bytes1, 0, random, 0, length);
                                    }
                                }
                            }
                            break;
                        case 3: //QKD
                            logger.info("QKD获取量子随机数");
                            KMSJNI kmsjni = new KMSJNI();
                            String qkdConfig = keySourceConfigs.get(i).getConfigInfo();
                            JSONObject configInfo = JSONObject.parseObject(qkdConfig);
                            JSONObject config = configInfo.getJSONObject("config");
                            JSONObject config2 = configInfo.getJSONObject("config2");
                            String localName = config.getString("localName");
                            String peerName = config.getString("peerName");
                            String devKey = config.getString("devKey");
                            String cryptKey = config.getString("cryptKey");
                            int num = length/32;
                            if (length%32 != 0)
                                num += 1;
                            byte[] bytes = new byte[num*32];
                            byte[] keyId = new byte[num*16];
                            if (localName != null && peerName != null && devKey != null && cryptKey != null){
                                bytes = kmsjni.kms_sdk_output_key_plaintext(num*32, keySourceConfigs.get(i).getSourceIp(),
                                        localName, peerName, HexUtils.hexStringToBytes(devKey),
                                        HexUtils.hexStringToBytes(cryptKey), false, keyId);
                            }
                            if (bytes != null) {
                                System.arraycopy(bytes, 0, random, 0, length);
                            }
                            if (Arrays.equals(random2,random)) {
                                localName = config2.getString("localName");
                                peerName = config2.getString("peerName");
                                devKey = config2.getString("devKey");
                                cryptKey = config2.getString("cryptKey");
                                if (keySourceConfigs.get(i).getSourceIp2() != null){
                                    bytes = kmsjni.kms_sdk_output_key_plaintext(num*32, keySourceConfigs.get(i).getSourceIp2(),
                                            localName, peerName, HexUtils.hexStringToBytes(devKey),
                                            HexUtils.hexStringToBytes(cryptKey), false, keyId);
                                }
                            }
                            if (bytes != null)
                                System.arraycopy(bytes, 0, random, 0, length);
                            break;
                        default:
                            random = randomByte(length);
                    }
                    if (!Arrays.equals(random,random2)) {
                        int finalKeySource = keySource;
                        CompletableFuture.runAsync(()->{
                            String rate = CalculateUtil.calRate(Integer.toUnsignedLong(length), System.currentTimeMillis() - startTime);
                            keySourceInfoService.updateKeySourceInfo(finalKeySource,rate,Integer.toUnsignedLong(length));
                        });
                        return random;
                    }
                }
            }
        }
        return randomByte(length);
    }

    /**
     * 根据指定长度生成字母和数字的随机数
     *   0~9的ASCII为48~57
     *   A~Z的ASCII为65~90
     *   a~z的ASCII为97~122
     * @param length
     * @return
     */
    public  String createRandomCharData(int length)
    {
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();//随机用以下三个随机生成器
        Random randdata=new Random();
        int data=0;
        for(int i=0;i<length;i++)
        {
            int index=rand.nextInt(3);
            //目的是随机选择生成数字，大小写字母
            switch(index)
            {
                case 0:
                    data=randdata.nextInt(10);//仅仅会生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data=randdata.nextInt(26)+65;//保证只会产生65~90之间的整数
                    sb.append((char)data);
                    break;
                case 2:
                    data=randdata.nextInt(26)+97;//保证只会产生97~122之间的整数
                    sb.append((char)data);
                    break;
            }
        }
        String result=sb.toString();
        return result;
    }


    public byte[] encryptCBC(byte[] plainTextBytes, String key) {
        try {
            SM4Util sm4Util = new SM4Util();
            return sm4Util.encryptDataCBC(plainTextBytes, key, SM4IV);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String encryptCBCWithPadding(String plainTextBytes, String key) {
        try {
            SM4Util sm4Util = new SM4Util();
            return HexUtils.bytesToHexString(sm4Util.encryptDataCBCWithPadding(plainTextBytes.getBytes(), key, SM4IV));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public byte[] decryptCBCWithPadding(byte[] cipherText, String key) {
        try {
            SM4Util sm4Util = new SM4Util();
            return sm4Util.decryptDataCBCWithPadding(cipherText, key, SM4IV);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decryptCBCWithPadding(String cipherText, String key) {
        try {
            SM4Util sm4Util = new SM4Util();
            return new String(sm4Util.decryptDataCBCWithPadding(HexUtils.hexStringToBytes(cipherText), key, SM4IV)).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private byte[] randomByte(int length){
        byte[] randomBytes = new byte[length];
        ThreadLocalRandom.current().nextBytes(randomBytes);
        return randomBytes;
    }


}


