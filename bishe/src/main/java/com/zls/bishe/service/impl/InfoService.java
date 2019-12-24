package com.zls.bishe.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zls.bishe.common.Threads;
import com.zls.bishe.common.org.n3r.idworker.Sid;
import com.zls.bishe.exception.BusException;
import com.zls.bishe.mapper.InfoMapper;
import com.zls.bishe.mapper.StudentMapper;
import com.zls.bishe.mapper.SysInfoMapper;
import com.zls.bishe.mapper.TeacherMapper;
import com.zls.bishe.pojo.Info;
import com.zls.bishe.pojo.Student;
import com.zls.bishe.pojo.SysInfo;
import com.zls.bishe.pojo.Teacher;
import com.zls.bishe.service.IInfoService;
import com.zls.bishe.utils.BeanUtils;
import com.zls.bishe.utils.PagedResult;
import com.zls.bishe.utils.TimeUtils;
import com.zls.bishe.vo.StudentDetailVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InfoService implements IInfoService {

    @Autowired
    private InfoMapper infoMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private SysInfoMapper sysInfoMapper;

    @Autowired
    private Sid sid;

    @Override
    public PagedResult selectStudentInfos(String start, String end, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        Example example = new Example(Info.class);
        Example.Criteria criteria = example.createCriteria();
        Date startDate = TimeUtils.parse(start, "yyyy-MM-dd");
        Date endDate = TimeUtils.parse(end, "yyyy-MM-dd");
        criteria.andBetween("updateTime", startDate, endDate);
        List<Info> infoList = infoMapper.selectByExample(example);

        List<StudentDetailVO> studentDetailVOList = new ArrayList<>(infoList.size());
        List<Student> infomations = null;
        for (Info info:infoList) {
            infomations = JSONObject.parseObject(info.getInformation(), new TypeReference<List<Student>>() {
            });
            StudentDetailVO studentDetailVO = new StudentDetailVO();
            BeanUtils.copyProperties(studentDetailVO, infomations.get(1));
            if (StringUtils.isNotEmpty(studentDetailVO.getUsername()) && !studentDetailVO.getUsername().equals(infomations.get(0).getUsername())) {
                studentDetailVO.setUsername(studentDetailVO.getUsername()+"(原："+infomations.get(0).getUsername()+")");
            }
            if (StringUtils.isNotEmpty(studentDetailVO.getClassName()) && !studentDetailVO.getClassName().equals(infomations.get(0).getClassName())) {
                studentDetailVO.setClassName(studentDetailVO.getClassName()+"(原："+infomations.get(0).getClassName()+")");
            }
            if (StringUtils.isNotEmpty(studentDetailVO.getIdNumber()) && !studentDetailVO.getIdNumber().equals(infomations.get(0).getIdNumber())) {
                studentDetailVO.setIdNumber(studentDetailVO.getIdNumber()+"(原："+infomations.get(0).getIdNumber()+")");
            }
            if (StringUtils.isNotEmpty(studentDetailVO.getRegisteredResidence()) && !studentDetailVO.getRegisteredResidence().equals(infomations.get(0).getRegisteredResidence())) {
                studentDetailVO.setRegisteredResidence(studentDetailVO.getRegisteredResidence()+"(原："+infomations.get(0).getRegisteredResidence()+")");
            }

            studentDetailVOList.add(studentDetailVO);
        }

        PageInfo<Info> pageInfo = new PageInfo<>(infoList);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(pageNo);
        pagedResult.setRecords(pageInfo.getTotal());
        pagedResult.setRows(studentDetailVOList);
        pagedResult.setTotal(pageInfo.getPages());
        return pagedResult;
    }

    @Transactional
    @Override
    public void reviewStudentInfo(Integer infoId) {
        Info info = infoMapper.selectByPrimaryKey(infoId);
        List<Student> students = JSONObject.parseObject(info.getInformation(), new TypeReference<List<Student>>() {
        });
        Student student = students.get(1);
        student.setReviewStatus(1);
        int i = studentMapper.updateByPrimaryKeySelective(student);
        if (i == 0){
            throw new BusException("审核失败！");
        }

        //审核通过
        Threads.dispatch(new Runnable() {
            @Override
            public void run() {
                info.setIsAdopt(1);
                infoMapper.updateByPrimaryKeySelective(info);
            }
        });

        //新增系统消息
        Threads.dispatch(new Runnable() {
            @Override
            public void run() {
                SysInfo sysInfo = new SysInfo();
                sysInfo.setId(sid.nextShort());
                sysInfo.setType(1);
                sysInfo.setMessage("同学你好！你于"+TimeUtils.date2String(info.getUpdateTime(), "yyyy年MM月dd日")+"修改的个人信息已审核成功！");
                sysInfo.setUserId(student.getUserId());
                sysInfo.setCreateTime(new Date());
                sysInfoMapper.insert(sysInfo);
            }
        });

    }

    @Transactional
    @Override
    public void reviewTeacherInfo(Integer infoId) {
        Info info = infoMapper.selectByPrimaryKey(infoId);
        List<Teacher> teachers = JSONObject.parseObject(info.getInformation(), new TypeReference<List<Teacher>>() {
        });
        Teacher teacher = teachers.get(1);
        teacher.setReviewStatus(1);
        int i = teacherMapper.updateByPrimaryKeySelective(teacher);
        if (i == 0){
            throw new BusException("审核失败！");
        }

        //审核通过
        Threads.dispatch(new Runnable() {
            @Override
            public void run() {
                info.setIsAdopt(1);
                infoMapper.updateByPrimaryKeySelective(info);
            }
        });

        //新增系统消息
        Threads.dispatch(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                SysInfo sysInfo = new SysInfo();
                sysInfo.setId(sid.nextShort());
                sysInfo.setType(1);
                sysInfo.setMessage("老师您！您于"+TimeUtils.date2String(info.getUpdateTime(), "yyyy年MM月dd日")+"修改的个人信息已审核成功！");
                sysInfo.setCreateTime(new Date());
                sysInfoMapper.insert(sysInfo);
            }
        });

    }

}
