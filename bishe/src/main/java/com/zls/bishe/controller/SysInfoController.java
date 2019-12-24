package com.zls.bishe.controller;

import com.zls.bishe.service.ISysInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Api(value="系统消息业务接口", tags= {"系统消息接口的controller"})
@Controller
@RequestMapping("/sysInfo")
public class SysInfoController extends BasicController {

    @Autowired
    private ISysInfoService sysInfoService;

    /*@ApiOperation("获取系统修改信息")
    @RequestMapping(value = "/queryReviewInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<PagedResult> queryReviewInfo(@RequestParam("userId") String userId){
        PagedResult result = sysInfoService.queryReviewInfo(userId, PAGE_NO, PAGE_SIZE);
        return ReturnResult.success(result);

    }

    @ApiOperation("获取系统通知信息")
    @RequestMapping(value = "/querySysInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<PagedResult> querySysInfo(){
        PagedResult result = sysInfoService.querySysInfo(PAGE_NO, PAGE_SIZE);
        return ReturnResult.success(result);

    }*/


}
