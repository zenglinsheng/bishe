package com.zls.bishe.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.zls.bishe.common.ReturnResult;
import com.zls.bishe.service.IStudentService;
import com.zls.bishe.utils.OfficeExportUtil;
import com.zls.bishe.utils.PagedResult;
import com.zls.bishe.vo.ExportStudentVO;
import com.zls.bishe.vo.StudentDetailVO;
import com.zls.bishe.vo.StudentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Api(value="学生业务接口", tags= {"学生业务接口的controller"})
@Controller
@RequestMapping(value = "/student")
public class StudentController extends BasicController{

    @Autowired
    private IStudentService studentService;

    @RequestMapping("/list")
    public String list(){
        return "pages/student/list";
    }

    @RequestMapping("/detail")
    public String detail(){
        return "pages/student/detail";
    }

    @RequestMapping("/edit")
    public String edit(){
        return "pages/student/edit";
    }

    @RequestMapping("/add")
    public String add(){
        return "pages/student/add";
    }

    @RequestMapping("/del")
    public String del() {
        return "pages/student/del";
    }

    @ApiOperation("获取学生个人信息")
    @RequestMapping(value = "/selectOne", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String selectOne(String userId){
        StudentDetailVO studentDetailVO = studentService.queryOne(userId);
        return ReturnResult.ok(studentDetailVO);
    }

    @ApiOperation("修改学生个人信息")
    @RequestMapping(value = "/updateOne", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<Void> updateOne(/*StudentDetailVO studentDetailVO*/){
        StudentDetailVO studentDetailVO = new StudentDetailVO();
        studentDetailVO.setUserId("001");
        studentDetailVO.setUsername("生哥");
        studentDetailVO.setRegisteredResidence("我爱我家");
        studentDetailVO.setClassName("18软件工程5班");
        studentDetailVO.setTelephone("16666666666");
        studentService.updateOne(studentDetailVO);
        return ReturnResult.success("修改信息已提交，请等待审核结果！");
    }

    @ApiOperation("管理员修改学生个人信息")
    @RequestMapping(value = "/updateOneByAdmin", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<Void> updateOneByAdmin(StudentDetailVO studentDetailVO){
        studentService.updateOneByAdmin(studentDetailVO);
        return ReturnResult.success("修改信息已提交，请等待审核结果！");
    }

    @ApiOperation("新增学生")
    @RequestMapping(value = "/addOne", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<Void> addOne(StudentVO studentVO) throws Exception{
        studentService.addOne(studentVO);
        return ReturnResult.success();
    }

    @ApiOperation("删除学生")
    @RequestMapping(value = "/deleteStudents", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String deleteStudents(String ids){
        studentService.deleteStudents(ids);
        return ReturnResult.ok();
    }

    @ApiOperation("恢复学生")
    @RequestMapping(value = "/recoverStudents", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String recoverStudents(String ids){
        studentService.recoverStudents(ids);
        return ReturnResult.ok();
    }

    @ApiOperation("获取班级学生")
    @RequestMapping(value = "/getStudentsOfClass", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<List<StudentVO>> getStudentsOfClass(@RequestParam Integer classId){
        List<StudentVO> list = studentService.queryStudentsByClassId(classId);
        return ReturnResult.success(list);
    }

    @ApiOperation("根据条件查询学生")
    @RequestMapping(value = "/selectStudents", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String selectStudents(StudentVO studentVO, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "10") int limit){
        PagedResult result = studentService.queryStudentsByConditions(studentVO, page, limit);
        return ReturnResult.success(result);
    }

    @ApiOperation("导出班级学生")
    @RequestMapping(value = "/exportStudentsOfClass", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void exportStudentsOfClass(@RequestParam Integer classId, HttpServletResponse response) throws Exception{
        List<StudentVO> list = studentService.queryStudentsByClassId(classId);
        exportStudents(list, response);
    }

    @ApiOperation("导出符合条件的学生")
    @RequestMapping(value = "/exportStudentsByConditions", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void exportStudentsByConditions(StudentVO studentVO, HttpServletResponse response) throws Exception{
        PagedResult result = studentService.queryStudentsByConditions(studentVO, PAGE_NO, Integer.MAX_VALUE);
        exportStudents((List<StudentVO>) result.getRows(), response);
    }

    @ApiOperation("导入学生")
    @RequestMapping(value = "/importStudents", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ReturnResult<Void> importStudents(@RequestParam("file") MultipartFile file) throws Exception{
        ImportParams importParams = new ImportParams();
        // 数据处理
        importParams.setHeadRows(1);
        importParams.setTitleRows(1);
        // 需要验证
        importParams.setNeedVerfiy(false);

        ExcelImportResult<ExportStudentVO> result = ExcelImportUtil.importExcelMore(file.getInputStream(), ExportStudentVO.class,
                    importParams);
        List<ExportStudentVO> studentVOList = result.getList();
        studentService.importStudents(studentVOList);

        return ReturnResult.success();
    }

    /*
        导出学生列表
     */
    private void exportStudents(List<StudentVO> studentList, HttpServletResponse response) throws Exception{
        List list = new ArrayList<>(studentList.size());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (StudentVO studentVO:studentList) {
            ExportStudentVO exportStudentVO = new ExportStudentVO();
            BeanUtils.copyProperties(studentVO, exportStudentVO);
            exportStudentVO.setGenderStr(studentVO.getGender() == 0 ? "男":"女");
            exportStudentVO.setBirthdayStr(sdf.format(studentVO.getBirthday()));
            list.add(exportStudentVO);
        }
        OfficeExportUtil.exportExcel(OfficeExportUtil.getWorkbook("学生信息", "学生信息", ExportStudentVO.class, list), "学生信息", response);
    }



}
