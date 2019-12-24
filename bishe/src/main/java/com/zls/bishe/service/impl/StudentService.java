package com.zls.bishe.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zls.bishe.common.Threads;
import com.zls.bishe.common.org.n3r.idworker.Sid;
import com.zls.bishe.exception.BusException;
import com.zls.bishe.mapper.ClassesMapper;
import com.zls.bishe.mapper.InfoMapper;
import com.zls.bishe.mapper.StudentMapper;
import com.zls.bishe.mapper.SysInfoMapper;
import com.zls.bishe.pojo.Classes;
import com.zls.bishe.pojo.Info;
import com.zls.bishe.pojo.Student;
import com.zls.bishe.pojo.SysInfo;
import com.zls.bishe.service.IClassesService;
import com.zls.bishe.service.IStudentService;
import com.zls.bishe.utils.*;
import com.zls.bishe.vo.ExportStudentVO;
import com.zls.bishe.vo.StudentDetailVO;
import com.zls.bishe.vo.StudentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StudentService implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ClassesMapper classesMapper;

    @Autowired
    private InfoMapper infoMapper;

    @Autowired
    private IClassesService classesService;

    @Autowired
    private SysInfoMapper sysInfoMapper;

    @Autowired
    private Sid sid;


    @Override
    public StudentDetailVO queryOne(String userId) {
        String[] maritals = new String[]{"未婚", "已婚","未知"};
        String[] politicals = new String[]{"群众", "中共团员","中共党员"};
        Student student = studentMapper.selectByPrimaryKey(userId);
        StudentDetailVO studentDetailVO = new StudentDetailVO();
        BeanUtils.copyProperties(studentDetailVO, student);
        studentDetailVO.setBirthdayStr(TimeUtils.date2String(student.getBirthday(), "yyyy-MM-dd"));
        studentDetailVO.setEntranceTimeStr(TimeUtils.date2String(student.getEntranceTime(), "yyyy-MM-dd"));
        studentDetailVO.setGenderStr(student.getGender()==0?"男":"女");
        studentDetailVO.setMaritalStatusStr(maritals[student.getMaritalStatus()]);
        studentDetailVO.setPoliticalAppearanceStr(politicals[student.getPoliticalAppearance()]);
        return studentDetailVO;
    }

    @Transactional
    @Override
    public void updateOne(StudentDetailVO studentDetailVO) {
        Student newStudent = new Student();
        BeanUtils.copyProperties(newStudent, studentDetailVO);
        newStudent.setBirthday(TimeUtils.parse(studentDetailVO.getBirthdayStr(), "yyyy-MM-dd"));
        newStudent.setEntranceTime(TimeUtils.parse(studentDetailVO.getEntranceTimeStr(), "yyyy-MM-dd"));
        Student oldStudent = studentMapper.selectByPrimaryKey(studentDetailVO.getUserId());

        //如果未修改关键信息则直接更新学生信息
        if (!oldStudent.getUsername().equals(newStudent.getUsername()) && !oldStudent.getClassName().equals(newStudent.getClassName()) &&
                !oldStudent.getIdNumber().equals(newStudent.getIdNumber()) && !oldStudent.getRegisteredResidence().equals(newStudent.getRegisteredResidence())) {
            Student baseStudent = new Student();
            BeanUtils.copyProperties(baseStudent, newStudent);
            baseStudent.setUsername(oldStudent.getUsername());
            baseStudent.setClassName(oldStudent.getClassName());
            baseStudent.setIdNumber(oldStudent.getIdNumber());
            baseStudent.setRegisteredResidence(oldStudent.getRegisteredResidence());
            //形式：第一条为基本信息修改，第二条为全部信息修改
            String json = JSONObject.toJSONString(Arrays.asList(baseStudent, newStudent));

            Info info = new Info();
            info.setInformation(json);
            //检测学生是否正在审核中,如果是则修改审核信息
            if(oldStudent.getReviewStatus() == 1){
                info.setInfoId(sid.nextShort());
                info.setUserId(oldStudent.getUserId());
                info.setType(0);
                info.setIsAdopt(0);
                info.setCreateTime(new Date());
                info.setUpdateTime(new Date());
                infoMapper.insertSelective(info);
            }else {
                Example example = new Example(Info.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("userId",oldStudent.getUserId());
                criteria.andEqualTo("isAdopt", 0);
                List<Info> infos = infoMapper.selectByExample(example);
                info.setInfoId(infos.get(0).getInfoId());
                info.setUpdateTime(new Date());
                infoMapper.updateByPrimaryKeySelective(info);
            }
        }
        studentMapper.updateByPrimaryKeySelective(newStudent);

    }

    @Transactional
    @Override
    public void updateOneByAdmin(StudentDetailVO studentDetailVO) {
        Student student = new Student();
        if(studentDetailVO.getClassId() == 0) {
            studentDetailVO.setClassId(null);
        }else {
            studentDetailVO.setClassName(classesService.queryOne(studentDetailVO.getClassId()).getClassname());
        }
        BeanUtils.copyProperties(student, studentDetailVO);
        student.setBirthday(TimeUtils.parse(studentDetailVO.getBirthdayStr(), "yyyy-MM-dd"));
        student.setEntranceTime(TimeUtils.parse(studentDetailVO.getEntranceTimeStr(), "yyyy-MM-dd"));
        studentMapper.updateByPrimaryKeySelective(student);
        Threads.dispatch(new Runnable() {
            @Override
            public void run() {
                String id = sid.nextShort();
                SysInfo sysInfo = new SysInfo();
                sysInfo.setId(id);
                sysInfo.setUserId(student.getUserId());
                sysInfo.setType(1);
                sysInfo.setMessage("同学你好！你的个人信息已于"+TimeUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss")+"进行系统更新，请及时核对个人信息！");
                sysInfo.setCreateTime(new Date());
                sysInfoMapper.insert(sysInfo);
            }
        });
    }


    @Transactional
    @Override
    public void addOne(StudentVO studentVO) throws Exception{
        Student student = new Student();
        BeanUtils.copyProperties(student, studentVO);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = sdf.parse(studentVO.getBirthdayStr());
        student.setBirthday(birthday);
        student.setUserId(sid.nextShort());
        String pwd = null;
        try {
            pwd = MD5Utils.getMD5Str(calPwd(birthday));
        } catch (Exception e) {
            throw new BusException("新增学生有误！");
        }
        Classes classes = classesService.queryOne(student.getClassId());
        student.setClassName(classes.getClassname());
        student.setPassword(pwd);
        student.setAge(TimeUtils.getAgeByBirth(student.getBirthday()));
        student.setReviewStatus(1);
        student.setDeleteStatus(0);
        student.setCreateTime(new Date());
        int i = studentMapper.insert(student);
        if(i == 0){
            throw new BusException("新增学生有误！");
        }

        /*
            对应班级人数加一
         */
        Threads.dispatch(new Runnable() {
            @Override
            public void run() {
                classesMapper.increaseNum(student.getClassId());
            }
        });


    }

    @Transactional
    @Override
    public void deleteStudents(String userIds) {
        String[] strs = userIds.split(",");
        ArrayList<String> userIdList = new ArrayList<>(strs.length);
        for (String userId:strs) {
            userIdList.add(userId);
        }
        int i = studentMapper.deleteStudents(userIdList);
        if(i == 0){
            throw new BusException("删除学生失败！");
        }
    }

    @Transactional
    @Override
    public void recoverStudents(String userIds) {
        String[] strs = userIds.split(",");
        ArrayList<String> userIdList = new ArrayList<>(strs.length);
        for (String userId:strs) {
            userIdList.add(userId);
        }
        int i = studentMapper.recoverStudents(userIdList);
        if(i == 0){
            throw new BusException("恢复学生失败！");
        }
    }

    @Override
    public List<StudentVO> queryStudentsByClassId(Integer classId) {
        Example example = new Example(Student.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("classId",classId);
        example.orderBy("number");
        List<Student> studentsList = studentMapper.selectByExample(example);
        List<StudentVO> list = new ArrayList<>(studentsList.size());
        for (Student student:studentsList) {
            StudentVO studentVO = new StudentVO();
            BeanUtils.copyProperties(studentVO,student);
            list.add(studentVO);
        }
        return list;
    }

    @Override
    public PagedResult queryStudentsByConditions(StudentVO studentVO, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        Example example = new Example(Student.class);
        Example.Criteria criteria = example.createCriteria();
        ExampleUtils.fillCondition(criteria, studentVO);
        example.orderBy("number").asc();
        List<Student> studentList = studentMapper.selectByExample(example);
        List<StudentVO> list = new ArrayList<>(studentList.size());
        for (Student student:studentList) {
            StudentVO stu = new StudentVO();
            BeanUtils.copyProperties(stu,student);
            list.add(stu);
        }
        PageInfo<Student> pageInfo = new PageInfo<>(studentList);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(pageNo);
        pagedResult.setRecords(pageInfo.getTotal());
        pagedResult.setRows(list);
        pagedResult.setTotal(pageInfo.getPages());
        return pagedResult;

    }

    @Transactional
    @Override
    public void importStudents(List<ExportStudentVO> exportList) throws Exception{
        List<Classes> classList = classesMapper.selectAll();
        Map<Object, Classes> classMap = BeanUtils.toMap(classList, "classname");

        List<Student> studentList = new ArrayList<>(exportList.size());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (ExportStudentVO exportStudentVO:exportList) {
            Student student = new Student();
            BeanUtils.copyProperties(student, exportStudentVO);
            student.setUserId(sid.nextShort());
            student.setGender("男".equals(exportStudentVO.getGenderStr()) ? 0 : 1);
            student.setBirthday(sdf.parse(exportStudentVO.getBirthdayStr()));
            String calPwd = calPwd(sdf.parse(exportStudentVO.getBirthdayStr()));
            student.setPassword(MD5Utils.getMD5Str(calPwd));
            student.setClassId(classMap.get(student.getClassName()).getClassId());
            student.setCreateTime(new Date());
            //对应班级人数加1
            classMap.get(exportStudentVO.getClassName()).setNum(classMap.get(exportStudentVO.getClassName()).getNum() + 1);
            studentList.add(student);
        }

        List<Classes> classesList = new ArrayList<>(classMap.size());
        for (Map.Entry<Object, Classes> edyth:classMap.entrySet()) {
            classesList.add(edyth.getValue());
        }

        int i = studentMapper.insertStudentList(studentList);
        if (i == 0) {
            throw new BusException("导入失败！");
        }

        classesMapper.updateClassList(classesList);


    }


    /*
    * 根据日期生成默认密码（如：19980223）
    */
    private static String calPwd(Date date){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sf.format(date);
        String[] split = dateStr.split("-");
        StringBuilder sb = new StringBuilder();
        for (String str:split) {
            sb.append(str);
        }
        return sb.toString();
    }

}
