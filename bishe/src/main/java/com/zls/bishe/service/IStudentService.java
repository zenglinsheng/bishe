package com.zls.bishe.service;

import com.zls.bishe.utils.PagedResult;
import com.zls.bishe.vo.ExportStudentVO;
import com.zls.bishe.vo.StudentDetailVO;
import com.zls.bishe.vo.StudentVO;

import java.util.List;

public interface IStudentService {

    StudentDetailVO queryOne(String userId);

    void updateOne(StudentDetailVO studentDetailVO);

    void updateOneByAdmin(StudentDetailVO studentDetailVO);

    void addOne(StudentVO studentVO) throws Exception;

    void deleteStudents(String userIds);

    void recoverStudents(String userIds);

    List<StudentVO> queryStudentsByClassId(Integer classId);

    PagedResult queryStudentsByConditions(StudentVO studentVO, int pageNo, int pageSize);

    void importStudents(List<ExportStudentVO> exportList) throws Exception;

}
