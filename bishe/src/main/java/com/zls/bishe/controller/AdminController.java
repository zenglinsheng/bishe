package com.zls.bishe.controller;

import com.zls.bishe.common.ReturnResult;
import com.zls.bishe.pojo.Admin;
import com.zls.bishe.pojo.Classes;
import com.zls.bishe.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(value="管理员业务接口", tags= {"管理员业务接口的controller"})
@Controller
@RequestMapping("/admin")
public class AdminController extends BasicController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation("获取管理员信息")
    @RequestMapping(value = "/selectOne", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<Classes> selectOne(@RequestParam("adminId") String adminId){
        Admin admin = adminService.queryOne(adminId);
        return ReturnResult.success(admin);
    }

    @ApiOperation("修改管理员信息")
    @RequestMapping(value = "/updateOne", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<Void> updateOne(@RequestBody Admin admin){
        adminService.updateOne(admin);
        return ReturnResult.success();
    }

}
