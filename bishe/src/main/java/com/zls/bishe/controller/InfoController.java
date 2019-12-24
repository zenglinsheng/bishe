package com.zls.bishe.controller;

import com.zls.bishe.common.ReturnResult;
import com.zls.bishe.service.impl.InfoService;
import com.zls.bishe.utils.PagedResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(value="信息业务接口", tags= {"信息业务接口的controller"})
@Controller
@RequestMapping("/info")
public class InfoController extends BasicController {

    @Autowired
    private InfoService infoService;

    @RequestMapping("/review")
    public String list(){
        return "pages/student/review";
    }

    @ApiOperation("获取学生审核信息")
    @RequestMapping(value = "/getStudentInfos", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<List> getStudentInfos(@RequestParam("start") String start, @RequestParam("end") String end, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "10") int limit){
        PagedResult result = infoService.selectStudentInfos(start, end, page, limit);
        return ReturnResult.success(result.getRows());
    }

    @ApiOperation("审核学生信息")
    @RequestMapping(value = "/reviewStudentInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<Void> reviewStudentInfo(@RequestParam("infoId") Integer infoId){
        infoService.reviewStudentInfo(infoId);
        return ReturnResult.success();
    }

    @ApiOperation("审核老师信息")
    @RequestMapping(value = "/reviewTeacherInfo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<Void> reviewTeacherInfo(@RequestParam("infoId") Integer infoId){
        infoService.reviewTeacherInfo(infoId);
        return ReturnResult.success();
    }

}
