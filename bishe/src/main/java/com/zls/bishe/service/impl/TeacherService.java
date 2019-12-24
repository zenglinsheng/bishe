package com.zls.bishe.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zls.bishe.common.Threads;
import com.zls.bishe.common.org.n3r.idworker.Sid;
import com.zls.bishe.exception.BusException;
import com.zls.bishe.mapper.InfoMapper;
import com.zls.bishe.mapper.SysInfoMapper;
import com.zls.bishe.mapper.TeacherMapper;
import com.zls.bishe.pojo.Info;
import com.zls.bishe.pojo.SysInfo;
import com.zls.bishe.pojo.Teacher;
import com.zls.bishe.service.ITeacherService;
import com.zls.bishe.utils.*;
import com.zls.bishe.vo.ExportTeacherVO;
import com.zls.bishe.vo.TeacherDetailVO;
import com.zls.bishe.vo.TeacherVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class TeacherService implements ITeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private InfoMapper infoMapper;

    @Autowired
    private SysInfoMapper sysInfoMapper;

    @Autowired
    private Sid sid;

    @Override
    public TeacherDetailVO queryOne(String userId) {
        Teacher teacher = teacherMapper.selectByPrimaryKey(userId);
        TeacherDetailVO teacherDetailVO = new TeacherDetailVO();
        BeanUtils.copyProperties(teacherDetailVO, teacher);
        teacherDetailVO.setBirthdayStr(TimeUtils.date2String(teacher.getBirthday(), "yyyy-MM-dd"));
        teacherDetailVO.setGenderStr(teacher.getGender()==0?"男":"女");
        filleExtraFields(teacherDetailVO, teacher);
        return teacherDetailVO;
    }

    private static TeacherDetailVO filleExtraFields(TeacherDetailVO teacherDetailVO, Teacher teacher){
        String[] maritals = new String[]{"未婚", "已婚","未知"};
        String[] politicals = new String[]{"群众", "中共团员","中共党员"};
        String[] titles = new String[]{"讲师", "助教","副教授","教授"};
        String[] degrees = new String[]{"学士", "硕士","博士"};
        if (teacher.getCompilation() != null) {
            teacherDetailVO.setCompilationStr(teacher.getCompilation()==0?"在编":"非在编");
        }
        if (teacher.getCurrentTitle() != null) {
            teacherDetailVO.setCurrentTitleStr(titles[teacher.getCurrentTitle()]);
        }
        if (teacher.getTopTitle() != null) {
            teacherDetailVO.setTopTitleStr(titles[teacher.getTopTitle()]);
        }
        if (teacher.getDepartureTime() != null) {
            teacherDetailVO.setDepartureTimeStr(TimeUtils.date2String(teacher.getDepartureTime(), "yyyy-MM-dd"));
        }
        if (teacher.getEntranceTime() != null) {
            teacherDetailVO.setEntranceTimeStr(TimeUtils.date2String(teacher.getEntranceTime(), "yyyy-MM-dd"));
        }
        if (teacher.getFirstDegree() != null) {
            teacherDetailVO.setFirstDegreeStr(degrees[teacher.getFirstDegree()]);
        }
        if (teacher.getHighestDegree() != null) {
            teacherDetailVO.setHighestDegreeStr(degrees[teacher.getHighestDegree()]);
        }
        if (teacher.getMaritalStatus() != null) {
            teacherDetailVO.setMaritalStatusStr(maritals[teacher.getMaritalStatus()]);
        }
        if (teacher.getPoliticalAppearance() != null) {
            teacherDetailVO.setPoliticalAppearanceStr(politicals[teacher.getPoliticalAppearance()]);
        }
        if (teacher.getJobStatus() != null) {
            teacherDetailVO.setJobStatusStr(teacher.getJobStatus()==0?"在职":"修业");
        }
        if (teacher.getWorkingTime() != null) {
            teacherDetailVO.setWorkingTimeStr(TimeUtils.date2String(teacher.getWorkingTime(), "yyyy-MM-dd"));
        }
        return teacherDetailVO;
    }

    @Transactional
    @Override
    public void updateOne(Teacher teacher) {
        Teacher oldteacher = teacherMapper.selectByPrimaryKey(teacher.getUserId());
        Teacher copyOldteacher = new Teacher();
        BeanUtils.copyProperties(copyOldteacher, oldteacher);

        //创建信息修改审核
        Threads.dispatch(new Runnable() {
            @Override
            public void run() {
                BeanUtils.contrast(copyOldteacher, teacher);
                copyOldteacher.setUsername(oldteacher.getUsername());
                copyOldteacher.setJobNumber(oldteacher.getJobNumber());
                teacher.setUsername(oldteacher.getUsername());
                teacher.setJobNumber(oldteacher.getJobNumber());
                String json = JSONObject.toJSONString(Arrays.asList(copyOldteacher, teacher));

                Info info = new Info();
                if (oldteacher.getIsLeader() == 0){
                    info.setType(1);
                }else {
                    info.setType(2);
                }
                info.setInfoId(sid.nextShort());
                info.setIsAdopt(0);
                info.setInformation(json);
                info.setCreateTime(new Date());
                info.setUpdateTime(new Date());
                infoMapper.insertSelective(info);
            }
        });
    }

    @Transactional
    @Override
    public void updateOneByAdmin(TeacherDetailVO teacherDetailVO) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacher, teacherDetailVO);
        teacher.setBirthday(TimeUtils.parse(teacherDetailVO.getBirthdayStr(), "yyyy-MM-dd"));
        teacher.setEntranceTime(TimeUtils.parse(teacherDetailVO.getEntranceTimeStr(), "yyyy-MM-dd"));
        teacher.setWorkingTime(TimeUtils.parse(teacherDetailVO.getWorkingTimeStr(), "yyyy-MM-dd"));
        teacherMapper.updateByPrimaryKeySelective(teacher);
        Threads.dispatch(new Runnable() {
            @Override
            public void run() {
                String id = sid.nextShort();
                SysInfo sysInfo = new SysInfo();
                sysInfo.setId(id);
                sysInfo.setUserId(teacher.getUserId());
                sysInfo.setType(1);
                sysInfo.setMessage("老师您好！您的个人信息已于"+TimeUtils.date2String(new Date(), "yyyy-MM-dd HH:mm:ss")+"进行系统更新，请及时核对个人信息！");
                sysInfo.setCreateTime(new Date());
                sysInfoMapper.insert(sysInfo);
            }
        });
    }

    @Transactional
    @Override
    public void addOne(TeacherVO teacherVO) throws Exception{
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacher, teacherVO);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = sdf.parse(teacherVO.getBirthdayStr());
        teacher.setBirthday(birthday);
        teacher.setUserId(sid.nextShort());
        String pwd = null;
        try {
            pwd = MD5Utils.getMD5Str(calPwd(teacher.getBirthday()));
        } catch (Exception e) {
            throw new BusException("新增老师有误！");
        }
        if(teacher.getCurrentTitle() == null){
            teacher.setCurrentTitle(0);
        }
        teacher.setPassword(pwd);
        teacher.setReviewStatus(1);
        teacher.setDeleteStatus(0);
        teacher.setAge(TimeUtils.getAgeByBirth(teacher.getBirthday()));
        teacher.setCreateTime(new Date());
        int i = teacherMapper.insert(teacher);
        if(i == 0){
            throw new BusException("新增老师有误！");
        }
    }

    @Transactional
    @Override
    public void deleteTeachers(String userIds) {
        String[] strs = userIds.split(",");
        ArrayList<String> userIdList = new ArrayList<>(strs.length);
        for (String userId:strs) {
            userIdList.add(userId);
        }
        int i = teacherMapper.deleteTeachers(userIdList);
        if(i == 0){
            throw new BusException("删除老师失败！");
        }
    }

    @Transactional
    @Override
    public void recoverTeachers(String userIds) {
        String[] strs = userIds.split(",");
        ArrayList<String> userIdList = new ArrayList<>(strs.length);
        for (String userId:strs) {
            userIdList.add(userId);
        }
        int i = teacherMapper.recoverTeachers(userIdList);
        if(i == 0){
            throw new BusException("删除老师失败！");
        }
    }

    @Override
    public PagedResult queryTeachersByConditions(TeacherVO teacherVO, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        Example example = new Example(Teacher.class);
        Example.Criteria criteria = example.createCriteria();
        ExampleUtils.fillCondition(criteria, teacherVO);

        List<Teacher> teacherList = teacherMapper.selectByExample(example);
        List<TeacherVO> list = new ArrayList<>(teacherList.size());
        for (Teacher teacher:teacherList) {
            TeacherVO tea = new TeacherVO();
            BeanUtils.copyProperties(tea,teacher);
            list.add(tea);
        }

        PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(pageNo);
        pagedResult.setRecords(pageInfo.getTotal());
        pagedResult.setRows(list);
        pagedResult.setTotal(pageInfo.getPages());
        return pagedResult;

    }

    @Transactional
    @Override
    public void importTeachers(List<ExportTeacherVO> exportList) throws Exception {
        List<Teacher> teacherList = new ArrayList<>(exportList.size());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (ExportTeacherVO exportTeacherVO:exportList) {
            Teacher teacher = new Teacher();
            BeanUtils.copyProperties(teacher, exportTeacherVO);
            teacher.setUserId(sid.nextShort());
            teacher.setJobNumber(exportTeacherVO.getJobNumber());
            teacher.setGender("男".equals(exportTeacherVO.getGenderStr()) ? 0 : 1);
            teacher.setBirthday(sdf.parse(exportTeacherVO.getBirthdayStr()));
            String calPwd = calPwd(sdf.parse(exportTeacherVO.getBirthdayStr()));
            teacher.setPassword(MD5Utils.getMD5Str(calPwd));
            teacher.setCreateTime(new Date());
            teacherList.add(teacher);
        }
        int i = teacherMapper.insertTeacherList(teacherList);
        if (i == 0) {
            throw new BusException("导入失败！");
        }
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
