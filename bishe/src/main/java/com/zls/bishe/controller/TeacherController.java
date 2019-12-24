package com.zls.bishe.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.zls.bishe.common.ReturnResult;
import com.zls.bishe.pojo.Teacher;
import com.zls.bishe.service.ITeacherService;
import com.zls.bishe.utils.OfficeExportUtil;
import com.zls.bishe.utils.PagedResult;
import com.zls.bishe.vo.ExportTeacherVO;
import com.zls.bishe.vo.TeacherDetailVO;
import com.zls.bishe.vo.TeacherVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Api(value="老师业务接口", tags= {"老师业务接口的controller"})
@Controller
@RequestMapping("/teacher")
public class TeacherController extends BasicController {

    @Autowired
    private ITeacherService teacherService;

    @RequestMapping("/list")
    public String list(){
        return "pages/teacher/list";
    }

    @RequestMapping("/edit")
    public String edit(){
        return "pages/teacher/edit";
    }

    @RequestMapping("/add")
    public String add(){
        return "pages/teacher/add";
    }

    @RequestMapping("/del")
    public String del() {
        return "pages/teacher/del";
    }

    @RequestMapping("/detail")
    public String detail(){
        return "pages/teacher/detail";
    }

    @ApiOperation("获取老师个人信息")
    @RequestMapping(value = "/selectOne", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String selectOne(String userId){
        TeacherDetailVO teacherDetailVO = teacherService.queryOne(userId);
        return ReturnResult.ok(teacherDetailVO);
    }

    @ApiOperation("修改老师个人信息")
    @RequestMapping(value = "/updateOne", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<Void> updateOne(@RequestBody Teacher teacher){
        teacherService.updateOne(teacher);
        return ReturnResult.success();
    }

    @ApiOperation("管理员修改老师个人信息")
    @RequestMapping(value = "/updateOneByAdmin", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<Void> updateOneByAdmin(TeacherDetailVO teacherDetailVO){
        teacherService.updateOneByAdmin(teacherDetailVO);
        return ReturnResult.success();
    }

    @ApiOperation("新增老师")
    @RequestMapping(value = "/addOne", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<Void> addOne(TeacherVO teacherVO) throws Exception{
        teacherService.addOne(teacherVO);
        return ReturnResult.success();
    }

    @ApiOperation("删除老师")
    @RequestMapping(value = "/deleteTeachers", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String deleteTeachers(String ids){
        teacherService.deleteTeachers(ids);
        return ReturnResult.ok();
    }

    @ApiOperation("删除老师")
    @RequestMapping(value = "/recoverTeachers", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String recoverTeachers(String ids){
        teacherService.recoverTeachers(ids);
        return ReturnResult.ok();
    }

    @ApiOperation("根据条件查询老师")
    @RequestMapping(value = "/selectTeachers", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String selectTeachers(TeacherVO teacherVO, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "10") int limit){
        PagedResult result = teacherService.queryTeachersByConditions(teacherVO, page, limit);
        return ReturnResult.success(result);
    }

    @ApiOperation("导出符合条件的老师")
    @RequestMapping(value = "/exportTeachersByConditions", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void exportTeachersByConditions(TeacherVO teacherVO, HttpServletResponse response) throws Exception{
        PagedResult result = teacherService.queryTeachersByConditions(teacherVO, PAGE_NO, Integer.MAX_VALUE);
        exportStudents((List<TeacherVO>) result.getRows(), response);
    }

    @ApiOperation("导入老师")
    @RequestMapping(value = "/importTeachers", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<Void> importTeachers(@RequestParam("file") MultipartFile file) throws Exception{
        ImportParams importParams = new ImportParams();
        // 数据处理
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);
        // 需要验证
        importParams.setNeedVerfiy(false);

        ExcelImportResult<ExportTeacherVO> result = ExcelImportUtil.importExcelMore(file.getInputStream(), ExportTeacherVO.class,
                    importParams);
        List<ExportTeacherVO> teacherVOList = result.getList();
        teacherService.importTeachers(teacherVOList);

        return ReturnResult.success();
    }

    /*
        导出老师列表
     */
    private void exportStudents(List<TeacherVO> teacherList, HttpServletResponse response) throws Exception{
        List list = new ArrayList<ExportTeacherVO>(teacherList.size());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (TeacherVO teacherVO:teacherList) {
            ExportTeacherVO exportTeacherVO = new ExportTeacherVO();
            BeanUtils.copyProperties(teacherVO, exportTeacherVO);
            exportTeacherVO.setGenderStr(teacherVO.getGender() == 0 ? "男":"女");
            exportTeacherVO.setBirthdayStr(sdf.format(teacherVO.getBirthday()));
            list.add(exportTeacherVO);
        }
        OfficeExportUtil.exportExcel(OfficeExportUtil.getWorkbook("老师信息", "老师信息", ExportTeacherVO.class, list), "老师信息", response);
    }


}
