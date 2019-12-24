package com.zls.bishe.controller;

import com.zls.bishe.common.ReturnResult;
import com.zls.bishe.pojo.Classes;
import com.zls.bishe.service.IClassesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Api(value="班级业务接口", tags= {"班级业务接口的controller"})
@Controller
@RequestMapping("/class")
public class ClassesController extends BasicController{

    @Autowired
    private IClassesService classesService;

    @ApiOperation("获取班级信息")
    @RequestMapping(value = "/selectOne", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<Classes> selectOne(@RequestParam("classId") Integer classId){
        Classes classes = classesService.queryOne(classId);
        return ReturnResult.success(classes);
    }

    @ApiOperation("获取所有班级")
    @RequestMapping(value = "/selectAllClasses", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<List<Classes>> selectAllClasses(){
        List<Classes> list = classesService.queryAllClasses();
        return ReturnResult.success(list);
    }

}
