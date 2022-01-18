package com.cucc.unicom.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppDeviceDTO implements Serializable {
    private int id;
    private int appId;
    private int deviceId;
    private String deviceName;
}
