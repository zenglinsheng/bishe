package com.zls.bishe.service;

import com.zls.bishe.pojo.Teacher;
import com.zls.bishe.utils.PagedResult;
import com.zls.bishe.vo.ExportTeacherVO;
import com.zls.bishe.vo.TeacherDetailVO;
import com.zls.bishe.vo.TeacherVO;

import java.util.List;

public interface ITeacherService {

    TeacherDetailVO queryOne(String userId);

    void updateOne(Teacher teacher);

    void updateOneByAdmin(TeacherDetailVO teacherDetailVO);

    void addOne(TeacherVO teacherVO) throws Exception;

    void deleteTeachers(String userIds);

    void recoverTeachers(String userIds);

    PagedResult queryTeachersByConditions(TeacherVO teacherVO, int pageNo, int pageSize);

    void importTeachers(List<ExportTeacherVO> exportList) throws Exception;

}
