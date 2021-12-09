package com.qtec.unicom;

import com.qtec.unicom.component.init.Init;
import com.qtec.unicom.pojo.IpInfo;
import com.qtec.unicom.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class UnicomServerApplication implements ApplicationRunner {

    @Autowired
    private IpService ipService;

    public static void main(String[] args) {
        SpringApplication.run(UnicomServerApplication.class,args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<IpInfo> allIps = ipService.getAllIps();
        if (allIps.size() != 0) {
            for (IpInfo ipInfo : allIps) {
                Init.IP_WHITE_SET.add(ipInfo.getIpInfo());
            }
        }
    }

}
