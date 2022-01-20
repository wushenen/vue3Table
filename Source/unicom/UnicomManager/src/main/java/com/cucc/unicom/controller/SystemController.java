package com.cucc.unicom.controller;

import com.cucc.unicom.component.Result;
import com.cucc.unicom.component.ResultHelper;
import com.cucc.unicom.pojo.CardData;
import com.cucc.unicom.pojo.LinuxServer;
import com.cucc.unicom.pojo.QkmVersion;
import com.cucc.unicom.service.SystemManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * 系统配置
 */
@Api(value = "系统配置接口",tags = {"系统配置接口"})
@RestController
@RequestMapping(value = "/system")
public class SystemController {
    private static final Logger logger = LoggerFactory.getLogger(SystemController.class);

    @Autowired
    private SystemManageService systemManageService;

    @Value("${systemVersion}")
    private String systemVersion;

    @ApiOperation(value = "查看系统信息",notes = "查看系统版本信息")
    @GetMapping("/getVersion")
    @ResponseBody
    public Result unicomGetVersion(){
        QkmVersion qkmVersion = systemManageService.getQkmVersion();
        HashMap<String, String> map = new HashMap<>();
        map.put("systemVersion",systemVersion);
        map.put("mysqlVersion","mysql-"+qkmVersion.getVersion());
        if (qkmVersion.getState() == 0) {
            map.put("systemStatus","初始");
        }else if (qkmVersion.getState() == 1){
            map.put("systemStatus","就绪");
        }else
            map.put("systemStatus","异常");
        return ResultHelper.genResultWithSuccess(map);
    }

    @ApiOperation(value = "修改本机ip,网关,掩码",notes = "修改本机ip,网关,掩码")
    @PostMapping("/updateIpNetmaskAndGateway")
    @ResponseBody
    public Result unicomUpdateIpNetmaskAndGateway(@RequestBody @Valid LinuxServer linuxServer) throws Exception{
        String result = systemManageService.updateIpNetmaskAndGateway(linuxServer);
        System.out.println(result);
        if (result.equals("0")) {
            return ResultHelper.genResultWithSuccess();
        } else if (result.equals("1")) {
            return ResultHelper.genResult(1, "ip冲突");
        }
        return ResultHelper.genResult(-1);
    }


    @ApiOperation(value = "系统初始化",notes = "系统初始化")
    @PostMapping("/init")
    @ResponseBody
    public Result unicomInit() throws Exception{
        String result = systemManageService.init();
        if (result.equals("0")) {
            return ResultHelper.genResultWithSuccess();
        } else if (result.equals("1")) {
            return ResultHelper.genResult(1, "系统已经初始化");
        }else
            return ResultHelper.genResult(1,"系统异常，请稍后再试");
    }


    @ApiOperation(value = "密码卡备份",notes = "密码卡备份")
    @PostMapping("/backUp")
    @ResponseBody
    public Result unicomBackUp(@RequestBody CardData cardData) throws Exception{
        systemManageService.backUp(cardData.getBackPass());
        return ResultHelper.genResultWithSuccess();
    }

    @ApiOperation(value = "密码卡还原",notes = "密码卡还原")
    @PostMapping("/restore")
    @ResponseBody
    public Result unicomRestore(@RequestBody CardData cardData) throws Exception{
        String result = systemManageService.restore(cardData);
        System.out.println(result);
        if (result.equals("0")) {
            return ResultHelper.genResultWithSuccess();
        } else {
            return ResultHelper.genResult(1, result);
        }
    }

    @ApiOperation(value = "列出所有密码卡备份信息",notes = "列出所有密码卡备份信息")
    @PostMapping("/listCardData/{offset}/{pageSize}")
    @ResponseBody
    public Result unicomListCardData(@RequestBody CardData cardData,
                               @PathVariable("offset") int offset,
                               @PathVariable("pageSize") int pageSize) throws Exception{
        if (pageSize > 50) {
            return ResultHelper.genResult(1, "pageSize过大");
        }
        PageHelper.startPage(offset,pageSize);
        List<CardData> result = systemManageService.listCardData(cardData);
        PageInfo<CardData> pageInfo = new PageInfo<>(result);
        return ResultHelper.genResultWithSuccess(pageInfo);
    }

}




